package controllers;

import collections.Locations;
import dao.LocationAccess;
import dao.ReceiptAccess;
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
import model.Location;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class LocationsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Location> locationsListView;

    @FXML
    private Button deleteLocationsButton;

    @FXML
    private TextField addLocationTextField;

    @FXML
    private Button addLocationButton;

    @FXML
    private Button backButton;

    @FXML
    void addLocationAction(ActionEvent event) {

        /* storage */
        int userid = LoginController.getUser().getUserID();
        String locationName = addLocationTextField.getText();

        /* empty check */
        if(locationName.isEmpty()) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Add Location Empty");
            a.setContentText("Please enter a location into the field.");

            Optional<ButtonType> aResult = a.showAndWait();
            if(aResult.isPresent() && aResult.get() == ButtonType.OK) {/* do nothing */}


        /* not empty */
        }else {

            /* add */
            LocationAccess.addLocation(locationName, userid);

            /* view maintenance */
            locationsListView.refresh();
            addLocationTextField.clear();
        }
    }

    @FXML
    void deleteLocationAction(ActionEvent event) {

        /* empty check */
        if(locationsListView.getSelectionModel().isEmpty()) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Selection Empty");
            a.setContentText("Please select a Location you wish to delete.");
            
            Optional<ButtonType> aResult = a.showAndWait();
            if(aResult.isPresent() && aResult.get() == ButtonType.OK) {/* do nothing */}

        /* not empty */
        }else {

            /* store truth */
            boolean result = ReceiptAccess.isLocationInUse(locationsListView.getSelectionModel().getSelectedItem().getLocationID(), LoginController.getUser().getUserID());

            /* check truth */
            if(result) {

                /* alert in use */
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Information");
                b.setHeaderText(locationsListView.getSelectionModel().getSelectedItem().getLocationName() + " Is In Use");
                b.setContentText("This category is currently in use and cannot be deleted.");

                Optional<ButtonType> bResult = b.showAndWait();
                if(bResult.isPresent() && bResult.get() == ButtonType.OK) {/* do nothing */}

            /* fail truth */
            }else {

                /* delete */
                LocationAccess.deleteLocation(locationsListView.getSelectionModel().getSelectedItem().getLocationID(), LoginController.getUser().getUserID());

                /* clears, queries, and builds list */
                LocationAccess.populateLocationsList(LoginController.getUser().getUserID());

                /* view maintenance */
                locationsListView.refresh();
            }
        }
    }
    
    @FXML
    void backAction(ActionEvent event) {

        /* change scene */
        SceneGrab.grabMain();
    }
    
    @FXML
    void initialize() {
        assert locationsListView != null : "fx:id=\"locationsListView\" was not injected: check your FXML file 'BB_locations.fxml'.";
        assert deleteLocationsButton != null : "fx:id=\"deleteLocationsButton\" was not injected: check your FXML file 'BB_locations.fxml'.";
        assert addLocationTextField != null : "fx:id=\"addLocationTextField\" was not injected: check your FXML file 'BB_locations.fxml'.";
        assert addLocationButton != null : "fx:id=\"addLocationButton\" was not injected: check your FXML file 'BB_locations.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'BB_locations.fxml'.";

        /* view maintenance */
        locationsListView.setItems(Locations.getLocationsList());
        locationsListView.refresh();
    }
}
