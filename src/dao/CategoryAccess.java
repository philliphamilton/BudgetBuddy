package dao;

import collections.Categories;
import utilities.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.SubCategory;

/**
 *
 * @author phami13@wgu.edu
 */
public class CategoryAccess {

    /* storage */
    protected static final String READ_CATEGORIES_BY_USER_ID = "SELECT cat_id, cat_name "
            + "FROM category "
            + "WHERE user_id = ?;";
    protected static final String READ_SUBCATEGORIES_BY_CAT_ID = "SELECT subcat_id, subcat_name "
            + "FROM subcategory "
            + "WHERE cat_id = ?;";
    protected static final String CREATE_CATEGORY_BY_USER_ID = "INSERT INTO category(cat_name, user_id) "
            + "VALUES(?, ?);";
    protected static final String CREATE_SUBCATEGORY_BY_CAT_ID = "INSERT INTO subcategory(cat_id, subcat_name) "
            + "VALUES(?, ?);";
    protected static final String DELETE_CATEGORY_BY_ID = "DELETE FROM category "
            + "WHERE cat_id = ?;";
    protected static final String DELETE_SUBCATEGORY_BY_ID = "DELETE FROM subcategory "
            + "WHERE subcat_id = ?;";

    public static void populateCategoryList(int userid) {

        /* clear list */
        Categories.clearCategoryList();
        
        try {

            /* prepare statement */
            PreparedStatement populateList_PS = DBConnection.getConnection().prepareStatement(READ_CATEGORIES_BY_USER_ID);
            populateList_PS.setInt(1, userid);


            /* result set */
            ResultSet populateList_RS = populateList_PS.executeQuery();

            /* loop result set */
            while(populateList_RS.next()){

                /* storage */
                int tempCatID = populateList_RS.getInt(1);
                String tempCatName = populateList_RS.getString(2);
                Category tempCategory = new Category(tempCatID, tempCatName);

                /* add to collection */
                Categories.getCategoriesList().add(tempCategory);
            }
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void populateSubCategoryList(int catid) {

        /* clear list */
        Categories.clearSubCategoryList();
        
        try{

            /* prepare statement */
            PreparedStatement populateSubCategory_PS = DBConnection.getConnection().prepareStatement(READ_SUBCATEGORIES_BY_CAT_ID);
            populateSubCategory_PS.setInt(1, catid);

            /* result set */
            ResultSet populateSubCategory_RS = populateSubCategory_PS.executeQuery();

            /* loop result set */
            while(populateSubCategory_RS.next()) {

                /* storage */
                int tempSubCatID = populateSubCategory_RS.getInt(1);
                String tempSubCatName = populateSubCategory_RS.getString(2);
                SubCategory tempSubCategory = new SubCategory(tempSubCatID, catid, tempSubCatName);

                /* add to collection */
                Categories.getSubCategoriesList().add(tempSubCategory);
            }
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void createCategory(String catname, int userid) {

        try{

            /* prepare statement */
            PreparedStatement createCategory_PS = DBConnection.getConnection().prepareStatement(CREATE_CATEGORY_BY_USER_ID, Statement.RETURN_GENERATED_KEYS);
            createCategory_PS.setString(1, catname);
            createCategory_PS.setInt(2, userid);
            createCategory_PS.executeUpdate();

            /* result set */
            ResultSet createCategory_RS = createCategory_PS.getGeneratedKeys();

            /* loop result set */
            while(createCategory_RS.next()){

                /* storage */
                int tempCatID = createCategory_RS.getInt(1);
                Category tempCategory = new Category(tempCatID, catname);

                /* add to collection */
                Categories.getCategoriesList().add(tempCategory);
            }
            
        }catch(SQLException e) {e.printStackTrace();}

    }

    public static void createSubCategory(int catid, String subcatname) {
        
        try{

            /* prepare statement */
            PreparedStatement createSubCategory_PS = DBConnection.getConnection().prepareStatement(CREATE_SUBCATEGORY_BY_CAT_ID, Statement.RETURN_GENERATED_KEYS);
            createSubCategory_PS.setInt(1, catid);
            createSubCategory_PS.setString(2, subcatname);
            createSubCategory_PS.executeUpdate();

            /* result set */
            ResultSet createSubCategory_RS = createSubCategory_PS.getGeneratedKeys();

            /* loop result set */
            while(createSubCategory_RS.next()){

                /* storage */
                int tempSubCatID = createSubCategory_RS.getInt(1);
                SubCategory tempSubCategory = new SubCategory(tempSubCatID, catid, subcatname);

                /* add to collection */
                Categories.getSubCategoriesList().add(tempSubCategory);
            }
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void deleteEntireCategoryTree(int categoryID) {

        /* storage */
        List<Integer> subcatIDArray = new ArrayList<Integer>();

        try{

            /* prepare statement */
            PreparedStatement populateSubCatIDArray_PS = DBConnection.getConnection().prepareStatement(READ_SUBCATEGORIES_BY_CAT_ID);
            populateSubCatIDArray_PS.setInt(1, categoryID);

            /* result set */
            ResultSet populateSubCatIDArray_RS = populateSubCatIDArray_PS.executeQuery();

            /* loop result set */
            while(populateSubCatIDArray_RS.next()){

                /* add to local collection */
                subcatIDArray.add(populateSubCatIDArray_RS.getInt(1));
            }

            /* empty check */
            if(subcatIDArray.isEmpty()){
                /* do nothing */

            /* not empty */
            }else {

                /* loop delete */
                for(Integer subCatId : subcatIDArray){

                    /* prepare statement */
                    PreparedStatement deleteSubCategory_PS = DBConnection.getConnection().prepareStatement(DELETE_SUBCATEGORY_BY_ID);
                    deleteSubCategory_PS.setInt(1, subCatId);
                    deleteSubCategory_PS.execute();
                    deleteSubCategory_PS.close();
                }

                /* clears, queries, and builds list */
                populateSubCategoryList(categoryID);
            }

            /* prepare statement */
            PreparedStatement deleteCategory_PS = DBConnection.getConnection().prepareStatement(DELETE_CATEGORY_BY_ID);
            deleteCategory_PS.setInt(1, categoryID);
            deleteCategory_PS.execute();
            
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void deleteSubCategory(int subCategoryID) {

        try{

            /* prepare statement */
            PreparedStatement deleteSubCategory_PS = DBConnection.getConnection().prepareStatement(DELETE_SUBCATEGORY_BY_ID);
            deleteSubCategory_PS.setInt(1, subCategoryID);
            deleteSubCategory_PS.execute();
            
        }catch(SQLException e) {e.printStackTrace();}
    }
}
