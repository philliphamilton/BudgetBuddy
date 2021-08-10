package controllers;

import collections.Members;
import dao.MemberAccess;
import dao.TransactionsAccess;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Member;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class MembersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Member> membersListView;

    @FXML
    private Button deleteMembersButton;

    @FXML
    private TextField addMembersTextField;

    @FXML
    private Button addMembersButton;

    @FXML
    private Button backButton;

    @FXML
    void addMembersAction(ActionEvent event) {

        /* storage */
        int userid = LoginController.getUser().getUserID();
        String memberName = addMembersTextField.getText();

        /* empty check */
        if(memberName.isEmpty()){

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Field Empty");
            a.setContentText("Please type a Member name you wish to add.");

            Optional<ButtonType> aResult = a.showAndWait();
            if (aResult.isPresent() && aResult.get() == ButtonType.OK) {

                /* view maintenance */
                a.close();
            }

        /* not empty */
        } else{

            /* add */
            MemberAccess.addMember(memberName, userid);

            /* view maintenance */
            membersListView.refresh();
            addMembersTextField.clear();
        }
    }

    @FXML
    void backAction(ActionEvent event) {

        /* change scene */
        SceneGrab.grabMain();
    }

    @FXML
    void deleteMembersAction(ActionEvent event) {

        /* empty check */
        if(membersListView.getSelectionModel().isEmpty()) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Selection Empty");
            a.setContentText("Please select a Member you wish to delete.");
            
            Optional<ButtonType> aResult = a.showAndWait();
            if (aResult.isPresent() && aResult.get() == ButtonType.OK) {

                /* view maintenance */
                a.close();
            }

        /* not empty */
        }else {

            /* store truth */
            boolean result = TransactionsAccess.isMemberInUse(membersListView.getSelectionModel().getSelectedItem().getMemeberID());

            /* check truth */
            if(result) {

                /* alert in use */
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Information");
                b.setHeaderText(membersListView.getSelectionModel().getSelectedItem().getMemberName() + " Is In Use");
                b.setContentText("This category is currently in use and cannot be deleted.");

                Optional<ButtonType> bResult = b.showAndWait();
                if (bResult.isPresent() && bResult.get() == ButtonType.OK) {

                    /* view maintenance */
                    b.close();
                }

            /* fail truth */
            } else{

                /* delete */
                MemberAccess.deleteMember(membersListView.getSelectionModel().getSelectedItem().getMemeberID(), LoginController.getUser().getUserID());

                /* clears, queries, and builds list */
                MemberAccess.populateMembersList(LoginController.getUser().getUserID());

                /* view maintenance */
                membersListView.refresh();
            }
        }
    }

    @FXML
    void initialize() {
        assert membersListView != null : "fx:id=\"membersListView\" was not injected: check your FXML file 'BB_members.fxml'.";
        assert deleteMembersButton != null : "fx:id=\"deleteMembersButton\" was not injected: check your FXML file 'BB_members.fxml'.";
        assert addMembersTextField != null : "fx:id=\"addMembersTextField\" was not injected: check your FXML file 'BB_members.fxml'.";
        assert addMembersButton != null : "fx:id=\"addMembersButton\" was not injected: check your FXML file 'BB_members.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'BB_members.fxml'.";

        /* view maintenance */
        membersListView.setItems(Members.getMembersList());
        membersListView.refresh();
    }
}
