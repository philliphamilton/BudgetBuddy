package controllers;

import collections.Month;
import dao.ProcurementAccess;
import dao.TransactionsAccess;
import dao.UserAccess;
import java.net.URL;
import java.time.YearMonth;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.User;
import utilities.DBBuild;
import utilities.SceneGrab;
import utilities.TestUser;

/**
 *
 * @author phami13@wgu.edu
 */
public class LoginController {

    /* Non-FXML Storage - Logged In User */
    protected static User loginUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private Button buttonNewUser;

    @FXML
    private PasswordField textFieldPassword;
    
    @FXML
    private VBox testInfoBox;
    
    @FXML
    private Label labelInstructions;
    
    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonTestData;

    @FXML
    void loginAction(ActionEvent event) {

        /* storage */
        String user = textFieldUsername.getText();
        String pass = textFieldPassword.getText();

        /* run security query */
        UserAccess.securityReadUser(user, pass);

        /* security truth */
        if((UserAccess.getPassSecurity()) == true) {

            /* storage */
            int userID = UserAccess.getUserID(user);
            String userName = user;
            int userNew = UserAccess.getNewUser(user);

            /* assign */
            setUser(new User(userID,userName,userNew));

            /* clear any previous list */
            Month.clearTransactionList();

            /* setup month */
            Month.setupYearMonths(YearMonth.now());

            /* populate transactions for current month */
            TransactionsAccess.populateTransactions(
                    Month.getFirstOfMonthTimestamp(), 
                    Month.getLastOfMonthTimestamp(),
                    getUser()
                    );

            /* populate transactions */
            ProcurementAccess.populateProcurements();

            /* change scene */
            SceneGrab.grabMain();

        /* security fail */
        }else {

            /* alert try again */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Invalid Credentials");
            a.setHeaderText("Try Again");
            a.setContentText("Please enter a valid username and password.");
            
            Optional<ButtonType> result = a.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                textFieldPassword.clear();
            }
        }
    }

    @FXML
    void newUserAction(ActionEvent event) {

        /* change scene */
        SceneGrab.grabNewUser();
    }
    
    @FXML
    void generateTestDataAction(ActionEvent event) {

        /* empty check */
        if(TestUser.doesTestUserExist()) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Test User Exists");
            a.setContentText("Please test as username and password.");
            
            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                textFieldUsername.setText("test");
                labelInstructions.setText("To log in with user \"test\", password = \"test\". Enjoy.");
                buttonTestData.setVisible(false);
            }

        /* not empty */
        }else {

            /* alert info */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Generating Test Data");
            a.setContentText("Username: \"test\" Password: \"test\"");
 
            Optional<ButtonType> result = a.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                a.close();
            }

            /* call for test data */
            DBBuild.generateTestData();

            /* view maintenance */
            textFieldUsername.setText("test");
            labelInstructions.setText("To log in with user \"test\", password = \"test\". Enjoy.");
            buttonTestData.setVisible(false);
            textFieldPassword.requestFocus();
        }
    }
    
    @FXML
    void initialize() {
        assert textFieldUsername != null : "fx:id=\"textFieldUsername\" was not injected: check your FXML file 'BB_login.fxml'.";
        assert buttonNewUser != null : "fx:id=\"buttonNewUser\" was not injected: check your FXML file 'BB_login.fxml'.";
        assert textFieldPassword != null : "fx:id=\"textFieldPassword\" was not injected: check your FXML file 'BB_login.fxml'.";
        assert buttonLogin != null : "fx:id=\"buttonLogin\" was not injected: check your FXML file 'BB_login.fxml'.";
        assert labelInstructions != null : "fx:id=\"labelInstructions\" was not injected: check your FXML file 'BB_login.fxml'.";
        assert testInfoBox != null : "fx:id=\"testInfoBox\" was not injected: check your FXML file 'BB_login.fxml'.";
        assert buttonTestData != null : "fx:id=\"buttonTestData\" was not injected: check your FXML file 'BB_login.fxml'.";

        /* view maintenance */
        buttonTestData.setDisable(false);
        labelInstructions.setDisable(false);
        /* view maintenance */
        if(TestUser.doesTestUserExist()) {
            labelInstructions.setText("To log in with user \"test\", password = \"test\". Enjoy.");
            buttonTestData.setVisible(false);
        }
        /* view maintenance */
        try {
            textFieldUsername.setText(LoginController.getUser().getUserName());
        }catch(NullPointerException e) {/* do nothing */}

    }

    private void setUser(User user) {
        loginUser =  user;
    }
    
    public static User getUser(){
        return loginUser;
    }
}
