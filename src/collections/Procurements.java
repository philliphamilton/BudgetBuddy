package collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Procurement;

/**
 *
 * @author phami13@wgu.edu
 */
public class Procurements {

    /* collection storage */
    private static ObservableList<Procurement> _procurements = FXCollections.observableArrayList();

    /* getter */
    public static ObservableList<Procurement> getProcurementsList() {
        return _procurements;
    }

    /* setter */
    public static void setProcurements(ObservableList<Procurement> procs) {
        _procurements.setAll(procs);
    }

    //utilities */
    public static void clearProcurementsList() {
        _procurements.clear();
    }
}
