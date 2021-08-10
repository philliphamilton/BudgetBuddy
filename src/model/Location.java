package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class Location {

    /* storage */
    private SimpleIntegerProperty _locationID = new SimpleIntegerProperty();
    private SimpleStringProperty _locationName = new SimpleStringProperty();

    public Location(int locid, String locname) {
        
        setLocationID(locid);
        setLocationName(locname);
    }

    private void setLocationID(int locid) {
        _locationID.set(locid);
    }

    private void setLocationName(String locname) {
        _locationName.set(locname);
    }

    public int getLocationID() {
        return _locationID.get();
    }
    
    public String getLocationName() {
       return _locationName.get();
    }
    
    public String toString() {
        return getLocationName();
    }


}
