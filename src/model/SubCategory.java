package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class SubCategory {

    /* storage */
    private SimpleIntegerProperty _subCategoryID = new SimpleIntegerProperty();
    private SimpleIntegerProperty _categoryID = new SimpleIntegerProperty();
    private SimpleStringProperty _subCategoryName = new SimpleStringProperty();
    
    public SubCategory(int subcatid, int catid, String subcatname) {

        setSubCategoryID(subcatid);
        setCategoryID(catid);
        setSubCategoryName(subcatname);
    }
    
    private void setSubCategoryID(int subcatid) {
        _subCategoryID.set(subcatid);
    }
    
    private void setCategoryID(int catid) {
        _categoryID.set(catid);
    }
    
    private void setSubCategoryName(String subcatname) {
        _subCategoryName.set(subcatname);
    }
    
    public int getSubCategoryID(){
        return _subCategoryID.get();
    }
    
    public String getSubCategoryName(){
        return _subCategoryName.get();
    }
    
    public String toString() {
        return getSubCategoryName();
    }
}
