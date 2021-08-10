package collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.SubCategory;

/**
 *
 * @author phami13@wgu.edu
 */
public class Categories {
    
    /* collection storage */
    private static ObservableList<Category> _categories = FXCollections.observableArrayList();
    private static ObservableList<SubCategory> _subCategories = FXCollections.observableArrayList();

    /* getters */
    public static ObservableList<Category> getCategoriesList() {
        return _categories;
    }
    public static ObservableList<SubCategory> getSubCategoriesList() {
        return _subCategories;
    }
    
    /* setters */
    public static void setSubCategoriesList(ObservableList<SubCategory> list) {
        _subCategories.setAll(list);
    }
    
    /* utilities */
    public static void clearSubCategoryList() {
        _subCategories.clear();
    }
    public static void clearCategoryList() {
        _categories.clear();
    }
}
