package controllers;

import collections.Categories;
import dao.CategoryAccess;
import dao.TransactionsAccess;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Category;
import model.SubCategory;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class CategoriesController {

    /* non FXML storage - category history */
    private static Category previousCategorySelection;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button categoriesDeleteButton;

    @FXML
    private ListView<Category> categoriesListView;

    @FXML
    private TextField categoriesTextField;

    @FXML
    private Button addCategoriesButton;

    @FXML
    private Button subCategoriesDeleteButton;

    @FXML
    private ListView<SubCategory> subCategoriesListView;

    @FXML
    private TextField subCategoriesTextField;

    @FXML
    private Button addSubCategoriesButton;

    @FXML
    private Button backButton;

    @FXML
    private Label labelSubCatName;

    @FXML
    void addCategoriesAction(ActionEvent event) {

        /* empty check */
        if(categoriesTextField.getText() == null || categoriesTextField.getText().equals("")) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Add Category Empty");
            a.setContentText("Please enter a category into the field.");
            Optional<ButtonType> aResult = a.showAndWait();
            if (aResult.isPresent() && aResult.get() == ButtonType.OK) {
                /* do nothing */
            }

        /* not empty */
        }else {

            /* category creation  */
            CategoryAccess.createCategory(categoriesTextField.getText(), LoginController.getUser().getUserID());

			/* view maintenance */
            subCategoriesListView.getItems().clear();
            subCategoriesListView.refresh();
            subCategoriesTextField.clear();
			labelSubCatName.setText("");

            /* view maintenance */
            categoriesListView.refresh();
            categoriesTextField.clear();
        }
    }

    @FXML
    void addSubCategoryAction(ActionEvent event) {

        /* empty check */
        if(subCategoriesTextField.getText() == null || subCategoriesTextField.getText().equals("")) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Add Sub-Category Empty");
            a.setContentText("Please enter a sub-category into the field.");
            Optional<ButtonType> aResult = a.showAndWait();
            if (aResult.isPresent() && aResult.get() == ButtonType.OK) {
                /* do nothing */
            }

        /* not empty */
        }else {
            /* subcategory creation */
            CategoryAccess.createSubCategory(previousCategorySelection.getCategoryID(), subCategoriesTextField.getText());

            /* view maintenance */
            subCategoriesListView.refresh();
            subCategoriesTextField.clear();
        }
        
    }

    @FXML
    void backAction(ActionEvent event) {

        /* change scene */
        SceneGrab.grabMain();
    }

    @FXML
    void categoriesDeleteButton(ActionEvent event) {

        /* empty check */
        if(categoriesListView.getSelectionModel().isEmpty()) {

            /* alert empty */
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Selection Empty");
            a.setContentText("Please select a Category you wish to delete.");
            
            Optional<ButtonType> aResult = a.showAndWait();
            if (aResult.isPresent() && aResult.get() == ButtonType.OK) {/* do nothing */}

        /* not empty */
        }else {

            /* store truth of category existence  */
            boolean result = TransactionsAccess.isCategoryInUse(categoriesListView.getSelectionModel().getSelectedItem().getCategoryID());
            
            /* check truth */
            if(result) {

                /* alert in use */
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Information");
                b.setHeaderText(categoriesListView.getSelectionModel().getSelectedItem().getCategoryName() + " Is In Use");
                b.setContentText("This category is currently in use and cannot be deleted.");
                
                Optional<ButtonType> bResult = b.showAndWait();
                if (bResult.isPresent() && bResult.get() == ButtonType.OK) {/* do nothing */}

            /* fail truth */
            }else {

                /* alert delete */
                Alert c = new Alert(Alert.AlertType.CONFIRMATION);
                c.setTitle("Confirm Delete");
                c.setHeaderText("Delete Category");
                c.setContentText("Would you like to delete this category and all assoicated\n"
                + "sub-categories?");
                ButtonType buttonYes = new ButtonType("Yes");
                ButtonType buttonNo = new ButtonType("No");
                c.getButtonTypes().setAll(buttonYes, buttonNo);
                Optional<ButtonType> cResult = c.showAndWait();

                /* choice yes */
                if(cResult.isPresent() && cResult.get() == buttonYes) {

                    /* delete category */
                    CategoryAccess.deleteEntireCategoryTree(categoriesListView.getSelectionModel().getSelectedItem().getCategoryID());

                    /* refresh list */
                    CategoryAccess.populateCategoryList(LoginController.getUser().getUserID());

                    /* view maintenance */
                    labelSubCatName.setText("");
                    subCategoriesListView.refresh();
                    categoriesListView.refresh();

                /* choice no */
                }else {
                    
                    c.close();
                }
            }
        }
    }

    @FXML
    void subCategoriesDeleteAction(ActionEvent event) {

        /* empty check */
        if(subCategoriesListView.getSelectionModel().isEmpty()) {
            
            //alert
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Information");
            a.setHeaderText("Selection Empty");
            a.setContentText("Please select a SubCategory you wish to delete.");
            
            Optional<ButtonType> aResult = a.showAndWait();
            if (aResult.isPresent() && aResult.get() == ButtonType.OK) {
                //do nothing
            }
        
        //pass empty test
        }else {

            /* store truth of sub-category existence */
            boolean result = TransactionsAccess.isSubCategoryInUse(subCategoriesListView.getSelectionModel().getSelectedItem().getSubCategoryID());

            /* check truth */
            if(result) {

                /* alert in use */
                Alert b = new Alert(Alert.AlertType.INFORMATION);
                b.setTitle("Information");
                b.setHeaderText(subCategoriesListView.getSelectionModel().getSelectedItem().getSubCategoryName() + " Is In Use");
                b.setContentText("This sub-category is currently in use and cannot be deleted.");

                Optional<ButtonType> bResult = b.showAndWait();
                if (bResult.isPresent() && bResult.get() == ButtonType.OK) {
                    /* do nothing */
                }

            /* fail truth */
            }else {

                /* delete sub-category */
                CategoryAccess.deleteSubCategory(subCategoriesListView.getSelectionModel().getSelectedItem().getSubCategoryID());

                /* refresh list */
                CategoryAccess.populateSubCategoryList(categoriesListView.getSelectionModel().getSelectedItem().getCategoryID());

                /* view maintenance */
                subCategoriesListView.refresh();
            }
        }
    }

    @FXML
    void selectedCategoryAction(MouseEvent event) {

        /* worthless execution check */
        if(previousCategorySelection == categoriesListView.getSelectionModel().getSelectedItem()) {
            /* do nothing */

        /* worth execution */
        }else {

            /* storage - last selected item */
            previousCategorySelection = categoriesListView.getSelectionModel().getSelectedItem();

            try{
                /* clears, queries, and builds list */
                CategoryAccess.populateSubCategoryList(categoriesListView.getSelectionModel().getSelectedItem().getCategoryID());

                /* view maintenance */
                labelSubCatName.setText(categoriesListView.getSelectionModel().getSelectedItem().toString());
            }catch(NullPointerException e) {/* do nothing */}

            /* view maintenance */
            subCategoriesListView.setItems(Categories.getSubCategoriesList());
            subCategoriesListView.refresh();
        }
    }
    
    @FXML
    void keyPressed(KeyEvent event) {

        /* worthless execution check */
        if(previousCategorySelection == categoriesListView.getSelectionModel().getSelectedItem()) {
            /* do nothing */

        /* worth execution */
        }else {

            /* storage - last selected item */
            previousCategorySelection = categoriesListView.getSelectionModel().getSelectedItem();

            try {
                /* clears, queries, and builds list */
                CategoryAccess.populateSubCategoryList(categoriesListView.getSelectionModel().getSelectedItem().getCategoryID());

                /* view maintenance */
                labelSubCatName.setText(categoriesListView.getSelectionModel().getSelectedItem().toString());
            }catch(NullPointerException e) {/* do nothing */}

            /* view maintenance */
            subCategoriesListView.setItems(Categories.getSubCategoriesList());
            subCategoriesListView.refresh();
        }
    }
    
    @FXML
    void actionConfirmCategory(ActionEvent event) {

        /* presses enter action */
        addCategoriesAction(event);
    }

    @FXML
    void actionConfirmSubCategory(ActionEvent event) {

        /* presses enter action */
        addSubCategoryAction(event);
    }
    
    @FXML
    void initialize() {
        assert categoriesDeleteButton != null : "fx:id=\"categoriesDeleteButton\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert categoriesListView != null : "fx:id=\"categoriesListView\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert categoriesTextField != null : "fx:id=\"categoriesTextField\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert addCategoriesButton != null : "fx:id=\"addCategoriesButton\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert subCategoriesDeleteButton != null : "fx:id=\"subCategoriesDeleteButton\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert subCategoriesListView != null : "fx:id=\"subCategoriesListView\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert subCategoriesTextField != null : "fx:id=\"subCategoriesTextField\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert addSubCategoriesButton != null : "fx:id=\"addSubCategoriesButton\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'BB_categories.fxml'.";
        assert labelSubCatName != null : "fx:id=\"labelSubCatName\" was not injected: check your FXML file 'BB_categories.fxml'.";

        /* view maintenance */
        categoriesListView.setItems(Categories.getCategoriesList());
        categoriesListView.refresh();
    }
}
