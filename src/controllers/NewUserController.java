package controllers;

import dao.UserAccess;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class NewUserController {
    
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private CheckBox checkBoxCategories;

    @FXML
    private CheckBox checkBoxLocations;

    @FXML
    private CheckBox checkBoxMembers;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    @FXML
    void cancelAction(ActionEvent event) {

        /* change scene */
        SceneGrab.grabLogin();
    }
    
    @FXML
    void hboxCategoriesAction(MouseEvent event) {

        /* QOL miss click */
        checkBoxCategories.fire();
    }

    @FXML
    void hboxLocationsAction(MouseEvent event) {

        /* QOL miss click */
        checkBoxLocations.fire();
    }

    @FXML
    void hboxMembersAction(MouseEvent event) {

        /* QOL miss click */
        checkBoxMembers.fire();
    }
    
    @FXML
    void saveAction(ActionEvent event) {

        /* storage */
        String name = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        /* store truth */
        boolean doesUserExists = UserAccess.doesUserExist(name);


        /* check truth */
        if (doesUserExists) {

            /* alert in use */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Invalid Credentials");
            a.setHeaderText("Username Is In Use");
            a.setContentText("Please choose a different user name.");

            Optional<ButtonType> result = a.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                /* view maintenance */
                a.close();
                textFieldUsername.clear();
            }

        /* fail truth */
        } else {

            /* check reserved */
            if (name.equals("test")) {

                /* alert in use */
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Invalid Credentials");
                a.setHeaderText("Reserved Username");
                a.setContentText("[test] is a reserved username.");

                Optional<ButtonType> result = a.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    /* view maintenance */
                    a.close();
                    textFieldUsername.clear();
                }

            /* not reserved */
            } else {

                /* empty check */
                if (name.equals("")) {

                    /* alert empty */
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Invalid Credentials");
                    a.setHeaderText("Try Again");
                    a.setContentText("Please enter a valid username.");

                    Optional<ButtonType> result = a.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        textFieldUsername.clear();
                    }

                /* not empty */
                } else {

                    //or some other more advanced password requirements
                    /* empty check */
                    if (password.equals("")) {

                        /* alert empty */
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Invalid Credentials");
                        a.setHeaderText("Try Again");
                        a.setContentText("Please enter a valid password.");

                        Optional<ButtonType> result = a.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {

                            /* view maintenance */
                            a.close();
                            textFieldPassword.clear();
                        }

                    /* not empty */
                    } else {

                        /* storage */
                        boolean loadCat = checkBoxCategories.selectedProperty().getValue();
                        boolean loadLoc = checkBoxLocations.selectedProperty().getValue();
                        boolean loadMem = checkBoxMembers.selectedProperty().getValue();

                        /* create */
                        UserAccess.createUser(name, password, loadCat, loadLoc, loadMem);

                        /* alert welcome */
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("New User Created");
                        a.setHeaderText("Welcome " + name + "!");
                        a.setContentText("Please enjoy the program. "
                                + "And keep your password safe. \nNo lost password support.");

                        Optional<ButtonType> result = a.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {

                            /* view maintenance */
                            SceneGrab.grabLogin();
                        }
                    }
                }
            }
        }
    }
    
    @FXML
    void initialize() {
        assert textFieldUsername != null : "fx:id=\"textFieldUsername\" was not injected: check your FXML file 'BB_newuser.fxml'.";
        assert textFieldPassword != null : "fx:id=\"textFieldPassword\" was not injected: check your FXML file 'BB_newuser.fxml'.";
        assert checkBoxCategories != null : "fx:id=\"checkBoxCategories\" was not injected: check your FXML file 'BB_newuser.fxml'.";
        assert checkBoxLocations != null : "fx:id=\"checkBoxLocations\" was not injected: check your FXML file 'BB_newuser.fxml'.";
        assert checkBoxMembers != null : "fx:id=\"checkBoxMembers\" was not injected: check your FXML file 'BB_newuser.fxml'.";
        assert buttonSave != null : "fx:id=\"buttonSave\" was not injected: check your FXML file 'BB_newuser.fxml'.";
        assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'BB_newuser.fxml'.";
    }
}
