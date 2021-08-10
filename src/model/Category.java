package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class Category {

    /* storage */
    private SimpleIntegerProperty _categoryID = new SimpleIntegerProperty();
    private SimpleStringProperty _categoryName = new SimpleStringProperty();
    
    public Category(int catid, String catname){
        
        setCategoryID(catid);
        setCategoryName(catname);
    }
    
    private void setCategoryID(int catid) {
        
        _categoryID.set(catid);
    }
    
    private void setCategoryName(String catname) {
        
        _categoryName.set(catname);
    }
    
    public int getCategoryID(){
        
        return _categoryID.get();
    }
    
    public String getCategoryName(){
        
        return _categoryName.get();
    }
    
    public String toString() {
        
        return getCategoryName();
    }
}
