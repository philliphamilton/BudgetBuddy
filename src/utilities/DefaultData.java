package utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phami13@wgu.edu
 */
public class DefaultData {

    /* storage procurements */
    protected static boolean doesProcurementExists = false;
    protected static final String[] _procurementArray = {"Expense", "Bill", "Subscription"};
    protected static final String _createProcurments = "INSERT INTO procurement (proc_name) "
            + "VALUES (?);";
    protected static final String _selectProcurments = "SELECT proc_name FROM procurement "
            + "WHERE proc_id = 1 OR proc_id = 2 OR proc_id = 3;";

    protected static void buildProcurements() {
        try{

            //SEE IF TEST USER EXISTS
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(_selectProcurments);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                doesProcurementExists = true;
            }
            if(doesProcurementExists) {
                //do nothing

                //MAKE PROCUREMENTS
            } else {
                for(int i = 0 ; i < (_procurementArray.length); i++ ) {
                    PreparedStatement psProcurement = DBConnection.getConnection().prepareStatement(_createProcurments);
                    psProcurement.setString(1, _procurementArray[i]);
                    psProcurement.executeUpdate();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    /* storage locations */
    protected static final String[] _locationArray = {"Grocery", "Bulk Supply", "Car Company", "Internet Company",
            "Phone Company", "Energy Company", "Bank", "Insurance", "Residence", "Mall", "Restaurant", "Gas Station", "Gym"};
    protected static final String _createLocations = "INSERT INTO location (loc_name, user_id) "
            + "VALUES (?,?);";

    public static void buildLocations(int userid) {
        try{
            for(int i = 0 ; i < (_locationArray.length); i++ ) {
                PreparedStatement pss = DBConnection.getConnection().prepareStatement(_createLocations);
                pss.setString(1, _locationArray[i]);
                pss.setInt(2, userid);
                pss.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    /* storage members */
    protected static final String[] _membersArray = {"Mom", "Dad", "Son", "Daughter", "Brother",
            "Sister", "Roommate", "Wife", "Husband", "Pet"};

    protected static final String _createMembers = "INSERT INTO member(mem_name, user_id) "
            + "VALUES (?,?);";

    public static void buildMembers(int userid) {
        try{
            for(int i = 0 ; i < (_membersArray.length); i++ ) {
                PreparedStatement pss = DBConnection.getConnection().prepareStatement(_createMembers);
                pss.setString(1, _membersArray[i]);
                pss.setInt(2, userid);
                pss.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /* storage category */
    protected static int cat_id = 9999;
    protected static final String[] _categoriesArray = {"Food", "Supplies", "Living", "Entertainment",
            "Shopping", "Health", "Beauty", "Medicine", "Transportation", "Pets", "Vacation"};
    protected static final String _createCategories = "INSERT INTO category(cat_name, user_id)"
            + "VALUES(?, ?)";
    protected static final String _selectCategoryID = "SELECT cat_id FROM category "
            + "WHERE cat_name = ?;";

    /* storage subcategory */
    protected static final String _createSubCategories = "INSERT INTO subcategory(cat_id, subcat_name)"
            + "VALUES (?, ?)";
    //1 Food
    protected static final String[] _subCategoryFood = {"Home", "Dining", "Snacks"};
    //2 Supplies
    protected static final String[] _subCategorySupplies = {"Cleaning", "Home", "Business"};
    //3 Living
    protected static final String[] _subCategoryLiving = {"Rent", "Mortgage", "Vehicle", "Insurance", "Electricity", "Water", "Internet"
            , "Phone"};
    //4 Entertainment
    protected static final String[] _subCategoryEntertainment = {"Online", "Movie", "E-Movie", "Game",
            "TV", "Apps"};
    //5 Shopping
    protected static final String[] _subCategoryShopping = {"Clothes", "Electronics", "Shoes", "Decor"};
    //6 Health
    protected static final String[] _subCategoryHealth = {"Gym", "Vitamins", "Supplements"};
    //7 Beauty
    protected static final String[] _subCategoryBeauty = {"Cosmetics", "Luxury", "Spa", "Treatments"};
    //8 Medicine
    protected static final String[] _subCategoryMedicine = {"Prescription", "Over-the-Counter", "Doctor"};
    //9 Transportation
    protected static final String[] _subCategoryTransportation = {"Gas", "Car","Uber", "Service", "Bus",
            "Airplane", "Maintenance"};
    //10 Pets
    protected static final String[] _subCategoryPets = {"Food", "Treats", "Toys", "Veterinarian"};
    //11 Vacation
    protected static final String[] _subCategoryVacation = {"Food", "Supplies", "Entertainment",
            "Shopping", "Health", "Beauty", "Medicine", "Transportation", "Pets"};

    public static void buildCategories(int userid) {
        try{
            for(int i = 0 ; i < (_categoriesArray.length); i++ ) {

                //LOAD FIRST CATEGORY
                PreparedStatement psCategories = DBConnection.getConnection().prepareStatement(_createCategories);
                psCategories.setString(1, _categoriesArray[i]);
                psCategories.setInt(2, userid);
                psCategories.executeUpdate();
                psCategories.close();

                //RETRIVE NEWLY LOADED CATEGORY ID
                PreparedStatement psSelectCatID = DBConnection.getConnection().prepareStatement(_selectCategoryID);
                psSelectCatID.setString(1, _categoriesArray[i]);
                ResultSet rsSelectCatID = psSelectCatID.executeQuery();
                while(rsSelectCatID.next()){
                    cat_id = rsSelectCatID.getInt(1);
                }

                //INNER LOOP THROUGH SUBCATEGORIES
                switch(_categoriesArray[i]) {

                    case "Food": //_subCategoryFood
                        for(int a = 0 ; a < (_subCategoryFood.length); a++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryFood[a]);
                            psSubCategories.executeUpdate();
                        }
                        break;
                    case "Supplies": //_subCategorySupplies
                        for(int b = 0 ; b < (_subCategorySupplies.length); b++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategorySupplies[b]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Living": //_subCategoryLiving
                        for(int z = 0 ; z < (_subCategoryLiving.length); z++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryLiving[z]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Entertainment": //_subCategoryEntertainment
                        for(int c = 0 ; c < (_subCategoryEntertainment.length); c++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryEntertainment[c]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Shopping": //_subCategoryShopping
                        for(int d = 0 ; d < (_subCategoryShopping.length); d++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryShopping[d]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Health": //_subCategoryHealth
                        for(int e = 0 ; e < (_subCategoryHealth.length); e++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryHealth[e]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Beauty": //_subCategoryBeauty
                        for(int f = 0 ; f < (_subCategoryBeauty.length); f++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryBeauty[f]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Medicine": //_subCategoryMedcine
                        for(int g = 0; g < (_subCategoryMedicine.length); g++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryMedicine[g]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Transportation": //_subCategoryTransportation
                        for(int h = 0 ; h < (_subCategoryTransportation.length); h++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryTransportation[h]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Pets": //_subCategoryPets
                        for(int j = 0 ; j < (_subCategoryPets.length); j++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryPets[j]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    case "Vacation": //_subCategoryVacation
                        for(int k = 0 ; k < (_subCategoryVacation.length); k++ ) {
                            PreparedStatement psSubCategories = DBConnection.getConnection().prepareStatement(_createSubCategories);
                            psSubCategories.setInt(1, cat_id);
                            psSubCategories.setString(2, _subCategoryVacation[k]);
                            psSubCategories.executeUpdate();
                        };
                        break;
                    default:
                        //do nothing
                        break;
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}

