package controllers;

import collections.Categories;
import collections.Locations;
import collections.Members;
import collections.Month;
import collections.Procurements;
import dao.CategoryAccess;
import dao.ReceiptAccess;
import dao.TransactionsAccess;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Category;
import model.Location;
import model.Member;
import model.SubCategory;
import model.Procurement;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class TransactionController {

    /* Non-FXML Storage - running split count */
    private static int _currentSplit = 0;

    /* Non-FXML Storage - main */
    private ObservableList<Category> _tempCategoriesMain = FXCollections.observableArrayList();
    private ObservableList<SubCategory> _tempSubCategoriesMain = FXCollections.observableArrayList();
    private ObservableList<Member> _tempMembersMain = FXCollections.observableArrayList();
    /* Non-FXML Storage - one */
    private ObservableList<Category> _tempCategoriesOne = FXCollections.observableArrayList();
    private ObservableList<SubCategory> _tempSubCategoriesOne = FXCollections.observableArrayList();
    private ObservableList<Member> _tempMembersOne = FXCollections.observableArrayList();
    /* Non-FXML Storage - two */
    private ObservableList<Category> _tempCategoriesTwo = FXCollections.observableArrayList();
    private ObservableList<SubCategory> _tempSubCategoriesTwo = FXCollections.observableArrayList();
    private ObservableList<Member> _tempMembersTwo = FXCollections.observableArrayList();
    /* Non-FXML Storage - three */
    private ObservableList<Category> _tempCategoriesThree = FXCollections.observableArrayList();
    private ObservableList<SubCategory> _tempSubCategoriesThree = FXCollections.observableArrayList();
    private ObservableList<Member> _tempMembersThree = FXCollections.observableArrayList();
    /* Non-FXML Storage - four */
    private ObservableList<Category> _tempCategoriesFour = FXCollections.observableArrayList();
    private ObservableList<SubCategory> _tempSubCategoriesFour = FXCollections.observableArrayList();
    private ObservableList<Member> _tempMembersFour = FXCollections.observableArrayList();
    /* Non-FXML Storage - five */
    private ObservableList<Category> _tempCategoriesFive = FXCollections.observableArrayList();
    private ObservableList<SubCategory> _tempSubCategoriesFive = FXCollections.observableArrayList();
    private ObservableList<Member> _tempMembersFive = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker datePickerMain;

    @FXML
    private ComboBox<Location> comboBoxLocationMain;

    @FXML
    private ComboBox<Category> comboBoxCategoryMain;

    @FXML
    private ComboBox<SubCategory> comboBoxSubCategoryMain;

    @FXML
    private TextField textFieldAmountMain;

    @FXML
    private ComboBox<Member> comboBoxMemberMain;

    @FXML
    private ComboBox<Procurement> comboBoxProcurementMain;

    @FXML
    private VBox boxSplitOne;

    @FXML
    private Button buttonSplitOne;

    @FXML
    private HBox boxDetailsOne;

    @FXML
    private ComboBox<Category> comboBoxCategoryOne;

    @FXML
    private ComboBox<SubCategory> comboBoxSubCategoryOne;

    @FXML
    private TextField textFieldAmountOne;

    @FXML
    private ComboBox<Member> comboBoxMemberOne;

    @FXML
    private VBox boxCancelOne;

    @FXML
    private Button buttonCancelSplitOne;

    @FXML
    private VBox boxSplitTwo;

    @FXML
    private Button buttonSplitTwo;

    @FXML
    private HBox boxDetailsTwo;

    @FXML
    private ComboBox<Category> comboBoxCategoryTwo;

    @FXML
    private ComboBox<SubCategory> comboBoxSubCategoryTwo;

    @FXML
    private TextField textFieldAmountTwo;

    @FXML
    private ComboBox<Member> comboBoxMemberTwo;

    @FXML
    private VBox boxCancelTwo;

    @FXML
    private Button buttonCancelSplitTwo;

    @FXML
    private VBox boxSplitThree;

    @FXML
    private Button buttonSplitThree;

    @FXML
    private HBox boxDetailsThree;

    @FXML
    private ComboBox<Category> comboBoxCategoryThree;

    @FXML
    private ComboBox<SubCategory> comboBoxSubCategoryThree;

    @FXML
    private TextField textFieldAmountThree;

    @FXML
    private ComboBox<Member> comboBoxMemberThree;

    @FXML
    private VBox boxCancelThree;

    @FXML
    private Button buttonCancelSplitThree;

    @FXML
    private VBox boxSplitFour;

    @FXML
    private Button buttonSplitFour;

    @FXML
    private HBox boxDetailsFour;

    @FXML
    private ComboBox<Category> comboBoxCategoryFour;

    @FXML
    private ComboBox<SubCategory> comboBoxSubCategoryFour;

    @FXML
    private TextField textFieldAmountFour;

    @FXML
    private ComboBox<Member> comboBoxMemberFour;

    @FXML
    private VBox boxCancelFour;

    @FXML
    private Button buttonCancelSplitFour;

    @FXML
    private VBox boxSplitFive;

    @FXML
    private Button buttonSplitFive;

    @FXML
    private HBox boxDetailsFive;

    @FXML
    private ComboBox<Category> comboBoxCategoryFive;

    @FXML
    private ComboBox<SubCategory> comboBoxSubCategoryFive;

    @FXML
    private TextField textFieldAmountFive;

    @FXML
    private ComboBox<Member> comboBoxMemberFive;

    @FXML
    private VBox boxCancelFive;

    @FXML
    private Button buttonCancelSplitFive;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonBack;

    @FXML
    void actionSave(ActionEvent event) {

        /* storage */
        int receiptID = 0;
        double tnxAmount = 0;
        double rcptAmount = 0;
        int count = 0;

        /* main empty check */
        if(comboBoxLocationMain.getSelectionModel().isEmpty()
                ||
                comboBoxProcurementMain.getSelectionModel().isEmpty()
                ||
                datePickerMain.getValue() == null) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Empty Fields");
            a.setContentText("Please enter Date, Location, Procurement.");

            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                /* view maintenance */
                a.close();
            }

        /* not empty */
        }else {

            /* secondary empty check */
            if(comboBoxCategoryMain.getSelectionModel().isEmpty()
                                ||
                                comboBoxSubCategoryMain.getSelectionModel().isEmpty()
                                ||
                                comboBoxMemberMain.getSelectionModel().isEmpty()
                                ||
                                textFieldAmountMain.getText().isEmpty()) {

                /* alert empty */
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Information");
                a.setHeaderText("Empty Fields");
                a.setContentText("Please enter Categories, Member, Amount.");

                Optional<ButtonType> result = a.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {

                    /* view maintenance */
                    a.close();
                }

            /* not empty */
            }else {

                /* add receipt */
                receiptID = ReceiptAccess.createReceipt(
                    LoginController.getUser().getUserID(),
                    comboBoxLocationMain.getSelectionModel().getSelectedItem().getLocationID(),
                    comboBoxProcurementMain.getSelectionModel().getSelectedItem().getProcurementID(),
                    datePickerMain.getValue(),
                    0);

                /* storage */
                tnxAmount = Double.valueOf(textFieldAmountMain.getText());

                /* add transaction */
                TransactionsAccess.createTransaction(receiptID,
                    comboBoxCategoryMain.getSelectionModel().getSelectedItem().getCategoryID(),
                    comboBoxSubCategoryMain.getSelectionModel().getSelectedItem().getSubCategoryID(),
                    comboBoxMemberMain.getSelectionModel().getSelectedItem().getMemeberID(),
                    tnxAmount);

                /* assign */
                rcptAmount = (tnxAmount + rcptAmount);

                /* storage important check */
                int badCount = 0;
                count = 1;

                /* loop splits from 1 */
                while(count <= _currentSplit) {

                    /* empty check or add transaction switch */
                    switch(count) {

                        /* One Split  */
                        case (1):

                            /* empty check */
                            if(comboBoxCategoryOne.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxSubCategoryOne.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxMemberOne.getSelectionModel().isEmpty()
                                    ||
                                    textFieldAmountOne.getText().isEmpty()) {

                                /* alert empty */
                                alertPopUp("First");

                                /* count */
                                badCount++;
                                count++;
                                break;

                            /* not empty */
                            }else {

                                /* storage */
                                tnxAmount = Double.valueOf(textFieldAmountOne.getText());

                                /* add transaction */
                                TransactionsAccess.createTransaction(receiptID,
                                    comboBoxCategoryOne.getSelectionModel().getSelectedItem().getCategoryID(),
                                    comboBoxSubCategoryOne.getSelectionModel().getSelectedItem().getSubCategoryID(),
                                    comboBoxMemberOne.getSelectionModel().getSelectedItem().getMemeberID(),
                                    tnxAmount);

                                /* assign */
                                rcptAmount = (tnxAmount + rcptAmount);

                                /* count */
                                count++;
                                break;
                            }

                        /* Two Split  */
                        case (2):

                            /* empty check */
                            if(comboBoxCategoryTwo.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxSubCategoryTwo.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxMemberTwo.getSelectionModel().isEmpty()
                                    ||
                                    textFieldAmountTwo.getText().isEmpty()) {

                                /* alert empty */
                                alertPopUp("Second");

                                /* count */
                                badCount++;
                                count++;
                                break;

                            /* not empty */
                            }else {

                                /* storage */
                                tnxAmount = Double.valueOf(textFieldAmountTwo.getText());

                                /* add transaction */
                                TransactionsAccess.createTransaction(receiptID,
                                    comboBoxCategoryTwo.getSelectionModel().getSelectedItem().getCategoryID(),
                                    comboBoxSubCategoryTwo.getSelectionModel().getSelectedItem().getSubCategoryID(),
                                    comboBoxMemberTwo.getSelectionModel().getSelectedItem().getMemeberID(),
                                    tnxAmount);

                                /* assign */
                                rcptAmount = (tnxAmount + rcptAmount);

                                /* count */
                                count++;
                                break;
                            }

                        /* Three Split  */
                        case (3):

                            /* empty check */
                            if(comboBoxCategoryThree.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxSubCategoryThree.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxMemberThree.getSelectionModel().isEmpty()
                                    ||
                                    textFieldAmountThree.getText().isEmpty()) {

                                /* alert empty */
                                alertPopUp("Third");

                                /* count */
                                badCount++;
                                count++;
                                break;

                            /* not empty */
                            }else {

                                /* storage */
                                tnxAmount = Double.valueOf(textFieldAmountThree.getText());

                                /* add transaction */
                                TransactionsAccess.createTransaction(receiptID,
                                    comboBoxCategoryThree.getSelectionModel().getSelectedItem().getCategoryID(),
                                    comboBoxSubCategoryThree.getSelectionModel().getSelectedItem().getSubCategoryID(),
                                    comboBoxMemberThree.getSelectionModel().getSelectedItem().getMemeberID(),
                                    tnxAmount);

                                /* assign */
                                rcptAmount = (tnxAmount + rcptAmount);

                                /* count */
                                count++;
                                break;
                            }

                        /* Four Split  */
                        case (4):

                            /* empty check */
                            if(comboBoxCategoryFour.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxSubCategoryFour.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxMemberFour.getSelectionModel().isEmpty()
                                    ||
                                    textFieldAmountFour.getText().isEmpty()) {

                                /* alert empty */
                                alertPopUp("Fourth");

                                /* count */
                                badCount++;
                                count++;
                                break;

                            /* not empty */
                            }else {

                                /* storage */
                                tnxAmount = Double.valueOf(textFieldAmountFour.getText());

                                /* add transaction */
                                TransactionsAccess.createTransaction(receiptID,
                                    comboBoxCategoryFour.getSelectionModel().getSelectedItem().getCategoryID(),
                                    comboBoxSubCategoryFour.getSelectionModel().getSelectedItem().getSubCategoryID(),
                                    comboBoxMemberFour.getSelectionModel().getSelectedItem().getMemeberID(),
                                    tnxAmount);

                                /* assign */
                                rcptAmount = (tnxAmount + rcptAmount);

                                /* count */
                                count++;
                                break;
                            }

                        /* Five Split  */
                        case (5):

                            /* empty check */
                            if(comboBoxCategoryFive.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxSubCategoryFive.getSelectionModel().isEmpty()
                                    ||
                                    comboBoxMemberFive.getSelectionModel().isEmpty()
                                    ||
                                    textFieldAmountFive.getText().isEmpty()) {

                                /* alert empty */
                                alertPopUp("Fifth");

                                /* count */
                                badCount++;
                                count++;
                                break;

                            /* not empty */
                            }else {

                                /* storage */
                                tnxAmount = Double.valueOf(textFieldAmountFive.getText());

                                /* add transaction */
                                TransactionsAccess.createTransaction(receiptID,
                                    comboBoxCategoryFive.getSelectionModel().getSelectedItem().getCategoryID(),
                                    comboBoxSubCategoryFive.getSelectionModel().getSelectedItem().getSubCategoryID(),
                                    comboBoxMemberFive.getSelectionModel().getSelectedItem().getMemeberID(),
                                    tnxAmount);

                                /* assign */
                                rcptAmount = (tnxAmount + rcptAmount);

                                /* count */
                                count++;
                                break;
                            }
                    }/* end of switch */
                }/* end of while loop */

                /* overreach fail */
                if(badCount > 0) {

                    /* delete receipt */
                    ReceiptAccess.deleteEntireReceipt(receiptID);

                /* overreach pass */
                }else {

                    /* refresh total */
                    ReceiptAccess.updateTotalAmountPaid(receiptID, rcptAmount);

                    /* clears, queries, and builds list */
                    TransactionsAccess.populateTransactions(Month.getFirstOfMonthTimestamp(), Month.getLastOfMonthTimestamp(), LoginController.getUser());

                    /* change scene */
                    SceneGrab.grabMain();
                }
            }
        }
    }

    @FXML
    void actionCancelSplitOne(ActionEvent event) {

        /* view maintenance */
        cancelOne();
    }

    @FXML
    void actionCancelSplitTwo(ActionEvent event) {

        /* view maintenance */
        cancelTwo();
    }

    @FXML
    void actionCancelSplitThree(ActionEvent event) {

        /* view maintenance */
        cancelThree();
    }

    @FXML
    void actionCancelSplitFour(ActionEvent event) {

        /* view maintenance */
        cancelFour();
    }

    @FXML
    void actionCancelSplitFive(ActionEvent event) {

        /* view maintenance */
        cancelFive();
    }

    @FXML
    void actionSplitOne(ActionEvent event) {

        /* view maintenance */
        splitOne();
    }

    @FXML
    void actionSplitTwo(ActionEvent event) {

        /* view maintenance */
        splitTwo();
    }

    @FXML
    void actionSplitThree(ActionEvent event) {

        /* view maintenance */
        splitThree();
    }

    @FXML
    void actionSplitFour(ActionEvent event) {

        /* view maintenance */
        splitFour();
    }

    @FXML
    void actionSplitFive(ActionEvent event) {

        /* view maintenance */
        splitFive();
    }
    
    @FXML
    void actionCategorySelectMain(ActionEvent event) {

        try{

            /* clears, queries, and builds list */
            CategoryAccess.populateSubCategoryList(comboBoxCategoryMain.getSelectionModel().getSelectedItem().getCategoryID());

            /* clears and sets local list */
            setTempSubCategoryListMain(Categories.getSubCategoriesList());

            /* view maintenance */
            comboBoxSubCategoryMain.setItems(_tempSubCategoriesMain);

        }catch (NullPointerException e) {/* do nothing */}
    }
    
    @FXML
    void actionCategorySelectOne(ActionEvent event) {

        try{

            /* clears, queries, and builds list */
            CategoryAccess.populateSubCategoryList(comboBoxCategoryOne.getSelectionModel().getSelectedItem().getCategoryID());

            /* clears and sets local list */
            setTempSubCategoryListOne(Categories.getSubCategoriesList());

            /* view maintenance */
            comboBoxSubCategoryOne.setItems(_tempSubCategoriesOne);
            
        }catch(NullPointerException e) {/* do nothing */}
    }
    
    @FXML
    void actionCategorySelectTwo(ActionEvent event) {

        try{

            /* clears, queries, and builds list */
            CategoryAccess.populateSubCategoryList(comboBoxCategoryTwo.getSelectionModel().getSelectedItem().getCategoryID());

            /* clears and sets local list */
            setTempSubCategoryListTwo(Categories.getSubCategoriesList());

            /* view maintenance */
            comboBoxSubCategoryTwo.setItems(_tempSubCategoriesTwo);
            
        }catch(NullPointerException e) {/* do nothing */}
    }
    
    @FXML
    void actionCategorySelectThree(ActionEvent event) {

        try{

            /* clears, queries, and builds list */
            CategoryAccess.populateSubCategoryList(comboBoxCategoryThree.getSelectionModel().getSelectedItem().getCategoryID());

            /* clears and sets local list */
            setTempSubCategoryListThree(Categories.getSubCategoriesList());

            /* view maintenance */
            comboBoxSubCategoryThree.setItems(_tempSubCategoriesThree);

        }catch(NullPointerException e) {/* do nothing */}
    }
    
    @FXML
    void actionCategorySelectFour(ActionEvent event) {

        try {

            /* clears, queries, and builds list */
            CategoryAccess.populateSubCategoryList(comboBoxCategoryFour.getSelectionModel().getSelectedItem().getCategoryID());

            /* clears and sets local list */
            setTempSubCategoryListFour(Categories.getSubCategoriesList());

            /* view maintenance */
            comboBoxSubCategoryFour.setItems(_tempSubCategoriesFour);

        }catch(NullPointerException e) {/* do nothing */}
    }
    
    @FXML
    void actionCategorySelectFive(ActionEvent event) {

        try{

            /* clears, queries, and builds list */
            CategoryAccess.populateSubCategoryList(comboBoxCategoryFive.getSelectionModel().getSelectedItem().getCategoryID());

            /* clears and sets local list */
            setTempSubCategoryListFive(Categories.getSubCategoriesList());

            /* view maintenance */
            comboBoxSubCategoryFive.setItems(_tempSubCategoriesFive);

        }catch(NullPointerException e) {/* do nothing */}
    }
    
    @FXML
    void actionBack(ActionEvent event) {

        /* change scene */
        SceneGrab.grabMain();
    }

    @FXML
    void initialize() {
        assert datePickerMain != null : "fx:id=\"datePickerMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxLocationMain != null : "fx:id=\"comboBoxLocationMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxCategoryMain != null : "fx:id=\"comboBoxCategoryMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxSubCategoryMain != null : "fx:id=\"comboBoxSubCategoryMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert textFieldAmountMain != null : "fx:id=\"textFieldAmountMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxMemberMain != null : "fx:id=\"comboBoxMemberMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxProcurementMain != null : "fx:id=\"comboBoxProcurementMain\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxSplitOne != null : "fx:id=\"boxSplitOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonSplitOne != null : "fx:id=\"buttonSplitOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxDetailsOne != null : "fx:id=\"boxDetailsOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxCategoryOne != null : "fx:id=\"comboBoxCategoryOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxSubCategoryOne != null : "fx:id=\"comboBoxSubCategoryOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert textFieldAmountOne != null : "fx:id=\"textFieldAmountOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxMemberOne != null : "fx:id=\"comboBoxMemberOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxCancelOne != null : "fx:id=\"boxCancelOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonCancelSplitOne != null : "fx:id=\"buttonCancelSplitOne\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxSplitTwo != null : "fx:id=\"boxSplitTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonSplitTwo != null : "fx:id=\"buttonSplitTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxDetailsTwo != null : "fx:id=\"boxDetailsTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxCategoryTwo != null : "fx:id=\"comboBoxCategoryTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxSubCategoryTwo != null : "fx:id=\"comboBoxSubCategoryTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert textFieldAmountTwo != null : "fx:id=\"textFieldAmountTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxMemberTwo != null : "fx:id=\"comboBoxMemberTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxCancelTwo != null : "fx:id=\"boxCancelTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonCancelSplitTwo != null : "fx:id=\"buttonCancelSplitTwo\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxSplitThree != null : "fx:id=\"boxSplitThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonSplitThree != null : "fx:id=\"buttonSplitThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxDetailsThree != null : "fx:id=\"boxDetailsThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxCategoryThree != null : "fx:id=\"comboBoxCategoryThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxSubCategoryThree != null : "fx:id=\"comboBoxSubCategoryThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert textFieldAmountThree != null : "fx:id=\"textFieldAmountThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxMemberThree != null : "fx:id=\"comboBoxMemberThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxCancelThree != null : "fx:id=\"boxCancelThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonCancelSplitThree != null : "fx:id=\"buttonCancelSplitThree\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxSplitFour != null : "fx:id=\"boxSplitFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonSplitFour != null : "fx:id=\"buttonSplitFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxDetailsFour != null : "fx:id=\"boxDetailsFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxCategoryFour != null : "fx:id=\"comboBoxCategoryFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxSubCategoryFour != null : "fx:id=\"comboBoxSubCategoryFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert textFieldAmountFour != null : "fx:id=\"textFieldAmountFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxMemberFour != null : "fx:id=\"comboBoxMemberFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxCancelFour != null : "fx:id=\"boxCancelFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonCancelSplitFour != null : "fx:id=\"buttonCancelSplitFour\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxSplitFive != null : "fx:id=\"boxSplitFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonSplitFive != null : "fx:id=\"buttonSplitFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxDetailsFive != null : "fx:id=\"boxDetailsFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxCategoryFive != null : "fx:id=\"comboBoxCategoryFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxSubCategoryFive != null : "fx:id=\"comboBoxSubCategoryFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert textFieldAmountFive != null : "fx:id=\"textFieldAmountFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert comboBoxMemberFive != null : "fx:id=\"comboBoxMemberFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert boxCancelFive != null : "fx:id=\"boxCancelFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonCancelSplitFive != null : "fx:id=\"buttonCancelSplitFive\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonSave != null : "fx:id=\"buttonSave\" was not injected: check your FXML file 'BB_transaction.fxml'.";
        assert buttonBack != null : "fx:id=\"buttonBack\" was not injected: check your FXML file 'BB_transaction.fxml'.";

        /* storage initalize */
        _tempMembersMain = Members.getMembersList();
        _tempCategoriesMain = Categories.getCategoriesList();
        //_tempSubCategoriesMain.clear();

        /* storage assign */
        _tempMembersOne = _tempMembersMain;
        _tempCategoriesOne = _tempCategoriesMain;
        //_tempSubCategoriesOne.clear();

        /* storage assign */
        _tempMembersTwo = _tempMembersMain;
        _tempCategoriesTwo = _tempCategoriesMain;
        //_tempSubCategoriesTwo.clear();

        /* storage assign */
        _tempMembersThree = _tempMembersMain;
        _tempCategoriesThree = _tempCategoriesMain;
        //_tempSubCategoriesThree.clear();

        /* storage assign */
        _tempMembersFour = _tempMembersMain;
        _tempCategoriesFour = _tempCategoriesMain;
        //_tempSubCategoriesFour.clear();

        /* storage assign */
        _tempMembersFive = _tempMembersMain;
        _tempCategoriesFive = _tempCategoriesMain;
        //_tempSubCategoriesFive.clear();

        /* assign split count */
        _currentSplit = 0;

        /* TextField only accepting 0-9 and a single decimal */
        /* thanks to user karimsqualli96 from https://gist.github.com/karimsqualli96/f8d4c2995da8e11496ed
           for this excellent double only filter */

        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9\\.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

        /* view maintenance */
        textFieldAmountMain.setTextFormatter(new TextFormatter<>(filter));
        textFieldAmountOne.setTextFormatter(new TextFormatter<>(filter));
        textFieldAmountTwo.setTextFormatter(new TextFormatter<>(filter));
        textFieldAmountThree.setTextFormatter(new TextFormatter<>(filter));
        textFieldAmountFour.setTextFormatter(new TextFormatter<>(filter));
        textFieldAmountFive.setTextFormatter(new TextFormatter<>(filter));

        /* view maintenance */
        defaultSetup();

        /* location */
        comboBoxLocationMain.setItems(Locations.getLocationsList());
        /* procurement */
        comboBoxProcurementMain.setItems(Procurements.getProcurementsList());
        /* members */
        comboBoxMemberMain.setItems(_tempMembersMain);
        /* categories */
        comboBoxCategoryMain.setItems(_tempCategoriesMain);
        comboBoxSubCategoryMain.setItems(_tempSubCategoriesMain);
    }

    private void defaultSetup() {

        /* view maintenance */
        /* One */
        boxSplitOne.setVisible(true);
        boxDetailsOne.setVisible(false);
        boxCancelOne.setVisible(false);
        /* Two */
        boxSplitTwo.setVisible(false);
        boxDetailsTwo.setVisible(false);
        boxCancelTwo.setVisible(false);
        /* Three */
        boxSplitThree.setVisible(false);
        boxDetailsThree.setVisible(false);
        boxCancelThree.setVisible(false);
        /* Four */
        boxSplitFour.setVisible(false);
        boxDetailsFour.setVisible(false);
        boxCancelFour.setVisible(false);
        /* Five */
        boxSplitFive.setVisible(false);
        boxDetailsFive.setVisible(false);
        boxCancelFive.setVisible(false);
    }
    
    private void splitOne(){

        /* view maintenance */
        /* One */
        boxSplitOne.setVisible(false);
        boxDetailsOne.setVisible(true);
        boxCancelOne.setVisible(true);
        /* Two */
        boxSplitTwo.setVisible(true);

        /* update split count */
        _currentSplit++;

        /* view maintenance */
        comboBoxSubCategoryOne.setItems(_tempSubCategoriesOne);
        comboBoxCategoryOne.setItems(_tempCategoriesOne);
        comboBoxMemberOne.setItems(_tempMembersOne);
    }
    
    private void splitTwo(){

        /* view maintenance */
        /* One */
        boxCancelOne.setVisible(false);
        /* Two */
        boxSplitTwo.setVisible(false);
        boxDetailsTwo.setVisible(true);
        boxCancelTwo.setVisible(true);
        /* Three */
        boxSplitThree.setVisible(true);

        /* update split count */
        _currentSplit++;

        /* view maintenance */
        comboBoxSubCategoryTwo.setItems(_tempSubCategoriesTwo);
        comboBoxCategoryTwo.setItems(_tempCategoriesTwo);
        comboBoxMemberTwo.setItems(_tempMembersTwo);
    }
    
    private void splitThree(){

        /* view maintenance */
        /* Two */
        boxCancelTwo.setVisible(false);
        /* Three */
        boxSplitThree.setVisible(false);
        boxDetailsThree.setVisible(true);
        boxCancelThree.setVisible(true);
        /* Four */
        boxSplitFour.setVisible(true);

        /* update split count */
        _currentSplit++;

        /* view maintenance */
        comboBoxSubCategoryThree.setItems(_tempSubCategoriesThree);
        comboBoxCategoryThree.setItems(_tempCategoriesThree);
        comboBoxMemberThree.setItems(_tempMembersThree);
    }
    
    private void splitFour(){

        /* view maintenance */
        /* Three */
        boxCancelThree.setVisible(false);
        /* Four */
        boxSplitFour.setVisible(false);
        boxDetailsFour.setVisible(true);
        boxCancelFour.setVisible(true);
        /* Five */
        boxSplitFive.setVisible(true);

        /* update split count */
        _currentSplit++;

        /* view maintenance */
        comboBoxSubCategoryFour.setItems(_tempSubCategoriesFour);
        comboBoxCategoryFour.setItems(_tempCategoriesFour);
        comboBoxMemberFour.setItems(_tempMembersFour);
    }
    
    private void splitFive(){

        /* view maintenance */
        /* Four */
        boxCancelFour.setVisible(false);
        /* Five */
        boxSplitFive.setVisible(false);
        boxDetailsFive.setVisible(true);
        boxCancelFive.setVisible(true);

        /* update split count */
        _currentSplit++;

        /* view maintenance */
        comboBoxSubCategoryFive.setItems(_tempSubCategoriesFive);
        comboBoxCategoryFive.setItems(_tempCategoriesFive);
        comboBoxMemberFive.setItems(_tempMembersFive);
    }
    
    private void cancelOne(){

        /* view maintenance */
        /* One */
        boxSplitOne.setVisible(true);
        boxDetailsOne.setVisible(false);
        boxCancelOne.setVisible(false);
        /* Two */
        boxSplitTwo.setVisible(false);

        /* update split count */
        _currentSplit--;
    }
    
    private void cancelTwo(){

        /* view maintenance */
        /* One */
        boxCancelOne.setVisible(true);
        /* Two */
        boxSplitTwo.setVisible(true);
        boxDetailsTwo.setVisible(false);
        boxCancelTwo.setVisible(false);
        /* Three */
        boxSplitThree.setVisible(false);

        /* update split count */
        _currentSplit--;
    }
    
    private void cancelThree(){

        /* view maintenance */
        /* Two */
        boxCancelTwo.setVisible(true);
        /* Three */
        boxSplitThree.setVisible(true);
        boxDetailsThree.setVisible(false);
        boxCancelThree.setVisible(false);
        /* Four */
        boxSplitFour.setVisible(false);

        /* update split count */
        _currentSplit--;
    }
    
    private void cancelFour(){

        /* view maintenance */
        /* Three */
        boxCancelThree.setVisible(true);
        /* Four */
        boxSplitFour.setVisible(true);
        boxDetailsFour.setVisible(false);
        boxCancelFour.setVisible(false);
        /* Five */
        boxSplitFive.setVisible(false);

        /* update split count */
        _currentSplit--;
    }
    
    private void cancelFive(){

        /* view maintenance */
        /* Four */
        boxCancelFour.setVisible(true);
        /* Five */
        boxSplitFive.setVisible(true);
        boxDetailsFive.setVisible(false);
        boxCancelFive.setVisible(false);

        /* update split count */
        _currentSplit--;
    }
    
    private void setTempSubCategoryListMain(ObservableList<SubCategory> ol){

        /* clears and sets list */
        _tempSubCategoriesMain.clear();
        _tempSubCategoriesMain.setAll(ol);
    }
    
    private void setTempSubCategoryListOne(ObservableList<SubCategory> ol){

        /* clears and sets list */
        _tempSubCategoriesOne.clear();
        _tempSubCategoriesOne.setAll(ol);
    }
    
    private void setTempSubCategoryListTwo(ObservableList<SubCategory> ol){

        /* clears and sets list */
        _tempSubCategoriesTwo.clear();
        _tempSubCategoriesTwo.setAll(ol);
    }
    
    private void setTempSubCategoryListThree(ObservableList<SubCategory> ol){

        /* clears and sets list */
        _tempSubCategoriesThree.clear();
        _tempSubCategoriesThree.setAll(ol);
    }
    
    private void setTempSubCategoryListFour(ObservableList<SubCategory> ol){

        /* clears and sets list */
        _tempSubCategoriesFour.clear();
        _tempSubCategoriesFour.setAll(ol);
    }
    
    private void setTempSubCategoryListFive(ObservableList<SubCategory> ol){

        /* clears and sets list */
        _tempSubCategoriesFive.clear();
        _tempSubCategoriesFive.setAll(ol);
    }

    private void alertPopUp(String splitNumber){

        /* alert empty */
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Missing Information");
        a.setHeaderText("Please Fill Out Each Row");
        a.setContentText(splitNumber + " split information missing");

        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            /* view maintenance */
            a.close();
        }
    }
    
}