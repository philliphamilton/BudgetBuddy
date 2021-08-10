package controllers;

import collections.Month;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import model.Transaction;
import dao.CategoryAccess;
import dao.LocationAccess;
import dao.MemberAccess;
import dao.ReceiptAccess;
import dao.TransactionsAccess;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class MainController {

    /* Non-FXML Storage - currency formatter */
    private static final DecimalFormat decimalFormatterCurrency = new DecimalFormat("#.##");
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonAddTransaction;

    @FXML
    private Button buttonDeleteTransaction;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private Button buttonReports;

    @FXML
    private Button buttonBackward;

    @FXML
    private Label labelMonthMain;

    @FXML
    private Label labelYearMain;
    
    @FXML
    private Label labelTotalAmount;

    @FXML
    private Button buttonFoward;

    @FXML
    private TableView<Transaction> tableViewTransactions;
    
    @FXML
    private TableColumn<Transaction, String> columnLocation;

    @FXML
    private TableColumn<Transaction, String> columnCategory;

    @FXML
    private TableColumn<Transaction, String> columnSubCategory;

    @FXML
    private TableColumn<Transaction, Double> columnAmount;

    @FXML
    private TableColumn<Transaction, String> columnMember;

    @FXML
    private TableColumn<Transaction, String> columnDate;

    @FXML
    private TableColumn<Transaction, String> columnProcurement;

    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonMembers;

    @FXML
    private Button buttonLocations;

    @FXML
    private Button buttonCategories;
      
    @FXML
    void locationsAction(ActionEvent event) {

        /* storage */
        int userid = LoginController.getUser().getUserID();

        /* clears, queries, and builds list */
        LocationAccess.populateLocationsList(userid);

        /* change scene */
        SceneGrab.grabLocations();
    }
    
    @FXML
    void membersAction(ActionEvent event) {

        /* storage */
        int userid = LoginController.getUser().getUserID();

        /* clears, queries, and builds list */
        MemberAccess.populateMembersList(userid);

        /* change scene */
        SceneGrab.grabMembers();
    }
    
    @FXML
    void categoriesAction(ActionEvent event) {

        /* storage */
        int userid = LoginController.getUser().getUserID();

        /* clears, queries, and builds list */
        CategoryAccess.populateCategoryList(userid);

        /* change scene */
        SceneGrab.grabCategories();
    }
    
    @FXML
    void addTransactionAction(ActionEvent event) {

        /* pre scene change collection refresh */
        populateCategories();
        populateMembers();
        populateLocations();

        /* change scene */
        SceneGrab.grabTransaction();
    }
    
    @FXML
    void deleteTransactionAction(ActionEvent event) {

        /* empty check */
        if(tableViewTransactions.getSelectionModel().isEmpty()) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Selection Empty");
            a.setContentText("Please select a transaction you wish to delete.");
            
            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                /* view maintenance */
                a.close();
            }

        /* not empty */
        }else {

            /* alert delete */
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.initStyle(StageStyle.DECORATED);
            a.setTitle("Confirm Delete");
            a.setHeaderText("Choose delete option.");
            a.setContentText("Selecting: \"Entire Receipt\" will delete ALL associated transactions."
                    + "\n           \"Single Transaction\" will remove the selected transaction.");
            ButtonType buttonEntire = new ButtonType("Entire Receipt");
            ButtonType buttonSingle = new ButtonType("Single Transaction");
            ButtonType buttonCancel = new ButtonType("Cancel");
            a.getButtonTypes().setAll(buttonEntire, buttonSingle, buttonCancel);
            Optional<ButtonType> result = a.showAndWait();

            /* choice entire */
            if(result.isPresent() && result.get() == buttonEntire) {

                /* view maintenance */
                a.close();

                /* alert delete */
                Alert b = new Alert(Alert.AlertType.CONFIRMATION);
                b.initStyle(StageStyle.DECORATED);
                b.setTitle("Confirm Delete");
                b.setHeaderText("Are you sure?.");
                b.setContentText("Selecting: \"Entire Receipt\" will delete ALL associated transactions."
                        + "\n           \"Single Transaction\" will remove the selected transaction.");
                ButtonType buttonYes = new ButtonType("Yes");
                ButtonType buttonNo = new ButtonType("No");
                b.getButtonTypes().setAll(buttonYes, buttonNo);
                Optional<ButtonType> bResult = b.showAndWait();

                /* choice yes */
                if(bResult.isPresent() && bResult.get() == buttonYes) {

                    /* delete */
                    ReceiptAccess.deleteEntireReceipt(tableViewTransactions.getSelectionModel().getSelectedItem().getRcptIDProperty().get());

                    /* clears, queries, and builds list */
                    TransactionsAccess.populateTransactions(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser());

                    /* view maintenance */
                    tableViewTransactions.refresh();
                    labelTotalAmount.setText(decimalFormatterCurrency.format(TransactionsAccess.totalByMonth(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser())));

                /* choice no */
                }else {

                    /* view maintenance */
                    b.close();
                }

            /* choice single */
            }else if(result.isPresent() && result.get() == buttonSingle) {

                /* check if receipt is tiny */
                if(TransactionsAccess.countTransactions(tableViewTransactions.getSelectionModel().getSelectedItem().getRcptIDProperty().get()) < 2) {

                    /* delete */
                    ReceiptAccess.deleteEntireReceipt(tableViewTransactions.getSelectionModel().getSelectedItem().getRcptIDProperty().get());

                /* receipt is large */
                }else {

                    /* storage - amount that is being deleted */
                    double bDouble = tableViewTransactions.getSelectionModel().getSelectedItem().getPaidProperty().doubleValue();

                    /* delete */
                    TransactionsAccess.deleteTransactionID(tableViewTransactions.getSelectionModel().getSelectedItem().getTnxIDProperty().get());

                    /* storage - total amount */
                    double aDouble = ReceiptAccess.findCurrentPaidTotal(tableViewTransactions.getSelectionModel().getSelectedItem().getRcptIDProperty().get());

                    /* assign */
                    aDouble = aDouble - bDouble;

                    /* update */
                    ReceiptAccess.updateTotalAmountPaid(tableViewTransactions.getSelectionModel().getSelectedItem().getRcptIDProperty().get(), aDouble);
                }

                /* clears, queries, and builds list */
                TransactionsAccess.populateTransactions(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser());

                /* view maintenance */
                tableViewTransactions.refresh();
                labelTotalAmount.setText(decimalFormatterCurrency.format(TransactionsAccess.totalByMonth(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser())));

            } else if(result.isPresent() && result.get() == buttonCancel){

                /* view maintenance */
                a.close();
            } else{

                /* view maintenance */
                a.close();
            }
        }
    }
    
    @FXML
    void backAction(ActionEvent event) {

        /* setup new month */
        Month.setupYearMonths(Month.getCurrentYearMonth().minusMonths(1));

        /* clear list */
        Month.getTransactionList().clear();

        /* populate list for new month */
        TransactionsAccess.populateTransactions(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser());

        /* reinitialize */
        initialize();

        /* view maintenance */
        if(textFieldSearch.getText() != null){
            String x = textFieldSearch.getText();
            textFieldSearch.clear();
            textFieldSearch.setText(x);
        }
    }
    
    @FXML
    void forwardAction(ActionEvent event) {

        /* setup new month */
        Month.setupYearMonths(Month.getCurrentYearMonth().plusMonths(1));

        /* clear list */
        Month.getTransactionList().clear();

        /* populate list for new month */
        TransactionsAccess.populateTransactions(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser());

        /* reinitialize */
        initialize();

        /* view maintenance */
        if(textFieldSearch.getText() != null){
            String x = textFieldSearch.getText();
            textFieldSearch.clear();
            textFieldSearch.setText(x);
        }
    }
    
    @FXML
    void exitAction(ActionEvent event) {

        /* alert exit */
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Shutdown Application?");
        a.setHeaderText("Yes or No");
        a.setContentText("This will shutdown the application."
                + "\nAll data will be saved locally in "
                + "\n"+ System.getenv("LOCALAPPDATA") +"\\BudgetBuddy");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        a.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = a.showAndWait();
        if(result.isPresent() && result.get() == buttonYes) {

            /* exit */
            Platform.exit();
        }else {
            a.close();
        }
    }

    @FXML
    void logoutAction(ActionEvent event) {

        /* change scene */
        SceneGrab.grabLogin();
    }

    @FXML
    void reportsAction(ActionEvent event) {

        /* setup month */
        Month.setupYearMonths(YearMonth.now());

        /* change scene */
        SceneGrab.grabReports();
    }

    @FXML
    void initialize() {
        assert buttonAddTransaction != null : "fx:id=\"buttonAddTransaction\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonDeleteTransaction != null : "fx:id=\"buttonDeleteTransaction\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert textFieldSearch != null : "fx:id=\"textFieldSearch\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonReports != null : "fx:id=\"buttonReports\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonBackward != null : "fx:id=\"buttonBackward\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert labelMonthMain != null : "fx:id=\"labelMonth\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert labelYearMain != null : "fx:id=\"labelYear\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert labelTotalAmount != null : "fx:id=\"labelTotalAmount\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonFoward != null : "fx:id=\"buttonFoward\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert tableViewTransactions != null : "fx:id=\"tableViewTransactions\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonLogout != null : "fx:id=\"buttonLogout\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonExit != null : "fx:id=\"buttonExit\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonMembers != null : "fx:id=\"buttonMembers\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonLocations != null : "fx:id=\"buttonLocations\" was not injected: check your FXML file 'BB_main.fxml'.";
        assert buttonCategories != null : "fx:id=\"buttonCategories\" was not injected: check your FXML file 'BB_main.fxml'.";

        /* view maintenance */
        labelMonthMain.setText(Month.getCurrentYearMonth().getMonth().name());
        labelYearMain.setText("" + Month.getCurrentYearMonth().getYear());
        buttonBackward.setText("< "+ Month.getPastYearMonth().getMonth().toString() + " " + Month.getPastYearMonth().getYear());
        buttonFoward.setText(Month.getFutureYearMonth().getMonth().toString() + " " + Month.getFutureYearMonth().getYear() + " >");

        /* data */
        columnLocation.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        columnCategory.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
        columnSubCategory.setCellValueFactory(cellData -> cellData.getValue().getSubCategoryProperty());
        columnAmount.setCellValueFactory(cellData -> cellData.getValue().getPaidProperty().asObject());
        columnMember.setCellValueFactory(cellData -> cellData.getValue().getMemberProperty());
        columnDate.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        columnProcurement.setCellValueFactory(cellData -> cellData.getValue().getProcurementProperty());

        /* view maintenance */
        labelTotalAmount.setText(decimalFormatterCurrency.format(TransactionsAccess.totalByMonth(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser())));

        /* storage */
        FilteredList<Transaction> filteredData = new FilteredList<>(Month.getTransactionList(), p -> true);

        /* Set the filter Predicate whenever the search changes */
        textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(myObject -> {

                /* Compare first name and last name field in your object with filter */
                String lowerCaseFilter = newValue.toLowerCase();

                /* find matching strings */
                if(String.valueOf(myObject.getLocationProperty()).toLowerCase().contains(lowerCaseFilter)
                    ||
                    String.valueOf(myObject.getMemberProperty()).toLowerCase().contains(lowerCaseFilter)
                    ||
                    String.valueOf(myObject.getProcurementProperty()).toLowerCase().contains(lowerCaseFilter)
                    ||
                    String.valueOf(myObject.getCategoryProperty()).toLowerCase().contains(lowerCaseFilter)
                    ||
                    String.valueOf(myObject.getSubCategoryProperty()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else{

                    /* view maintenance */
                    labelTotalAmount.setText(decimalFormatterCurrency.format(TransactionsAccess.totalByMonth(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser())));
                    return false;
                }
            });

            /* storage */
            double filteredAmount = 0.0;

            /* loop for total */
            for(Transaction tnx : filteredData) {

                filteredAmount = filteredAmount + tnx.getPaidProperty().get();
            }

            /* view maintenance */
            labelTotalAmount.setText(decimalFormatterCurrency.format(filteredAmount));
        });

        /* wrap the FilteredList in a SortedList */
        SortedList<Transaction> sortedData = new SortedList<>(filteredData);

         /* bind the SortedList comparator to the TableView comparator */
        sortedData.comparatorProperty().bind(tableViewTransactions.comparatorProperty());

        /* view maintenance */
        tableViewTransactions.setItems(sortedData);
    }

    private void populateLocations() {

        /* storage */
        int userid = LoginController.getUser().getUserID();

        /* clears, queries, and builds list */
        LocationAccess.populateLocationsList(userid);
    }
    
    private void populateCategories() {

        /* storage */
        int userid = LoginController.getUser().getUserID();

        /* clears, queries, and builds list */
        CategoryAccess.populateCategoryList(userid);
    }
    
    private void populateMembers() {

        /* storage */
        int userid = LoginController.getUser().getUserID();

        /* clears, queries, and builds list */
        MemberAccess.populateMembersList(userid);
    }

}
