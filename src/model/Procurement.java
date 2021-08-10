package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class Procurement {

    /* storage */
    private SimpleIntegerProperty _procurementID = new SimpleIntegerProperty();
    private SimpleStringProperty _procurementName = new SimpleStringProperty();
    
    public Procurement(int proid, String proname) {
        
        setProcurementID(proid);
        setProcurementName(proname);
    }

    private void setProcurementID(int proid) {
        _procurementID.set(proid);
    }

    private void setProcurementName(String proname) {
        _procurementName.set(proname);
    }
    
    public int getProcurementID() {
       return _procurementID.get();
    }
    
    public String getProcurementName() {
       return _procurementName.get();
    }
    
    public String toString() {
        return getProcurementName();
    }
}
