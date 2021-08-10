package collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Location;

/**
 *
 * @author phami13@wgu.edu
 */
public class Locations {
    
    /* collection storage */
    private static ObservableList<Location> _locations = FXCollections.observableArrayList();
    
    /* getter */
    public static ObservableList<Location> getLocationsList() {
        return _locations;
    }
    
    /* setter */
    public static void setLocations(ObservableList<Location> locs) {
        _locations.setAll(locs);
    }
    
    /* utilities */
    public static void clearLocationsList() {
        _locations.clear();
    }
    
}
