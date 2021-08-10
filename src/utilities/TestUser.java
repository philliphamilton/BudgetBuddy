package utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 *
 * @author phami13@wgu.edu
 */
public class TestUser {

    /* storage user */
    protected static final String _userName = "test";
    protected static final String  _password = "test";
    protected static int _userID = 99999;
    protected static int _newUser = 1;

    protected static final String _selectTestUser_isNewUser = "SELECT new_user "
            + "FROM user "
            + "WHERE name = \"test\";";
    protected static final String _selectTestUser_userID = "SELECT user_id "
            + "FROM user "
            + "WHERE name = ?;";
    protected static final String _createTestUser = "INSERT INTO user "
            + "(name, password, new_user) "
            + "VALUES (?,?,?);";

    public static boolean doesTestUserExist() {

        /* storage */
        boolean doesTestUserExist = false;

        try {

            /* prepare statement */
            PreparedStatement readUserPS = DBConnection.getConnection().prepareStatement(_selectTestUser_userID);
            readUserPS.setString(1, _userName);

            /* result set */
            ResultSet doesTestUserExistRS = readUserPS.executeQuery();

            /* loop result set */
            while(doesTestUserExistRS.next()) {
                /* assign */
                doesTestUserExist = true;
            }
        }catch(SQLException e) {e.printStackTrace();}

        return doesTestUserExist;
    }

    public static void buildTestUser() {

        try {

            /* prepare statement */
            PreparedStatement psMakeTestUser = DBConnection.getConnection().prepareStatement(_createTestUser, Statement.RETURN_GENERATED_KEYS);
            psMakeTestUser.setString(1, _userName);
            psMakeTestUser.setString(2, _password);
            psMakeTestUser.setInt(3, _newUser);
            psMakeTestUser.executeUpdate();

            /* result set */
            ResultSet generatedKeys = psMakeTestUser.getGeneratedKeys();

            /* loop result set */
            while(generatedKeys.next()) {
                /* assign */
                _userID = generatedKeys.getInt(1);
            }
        }catch(SQLException ex) {ex.printStackTrace();}
    }

    /* storage transactions */
    protected static final String _createReceipt = "INSERT INTO receipt "
            + "(user_id, loc_id, proc_id, date, total_amount_paid) "
            + "VALUES (?,?,?,?,?);";
    protected static final String _createTransaction = "INSERT INTO transactions "
            + "(rcpt_id, cat_id, subcat_id, mem_id, amount_paid) "
            + "VALUES (?,?,?,?,?);";

    public static void buildReceipts(int userID) {

        /* current year month */
        YearMonth ymnow = YearMonth.now();

        /* previous year month */
        YearMonth lmnow = ymnow.minusMonths(1);

        /* assign first timestamp */
        Timestamp firstOfCurrentMonth = Timestamp.valueOf(
                LocalDateTime.of(ymnow.getYear(), ymnow.getMonthValue(), 01, 12, 00));

        /* assign second timestamp */
        Timestamp lastOfPreviousMonth = Timestamp.valueOf(
                LocalDateTime.of(lmnow.getYear(), lmnow.getMonthValue(), lmnow.lengthOfMonth(), 12, 00));

        /* all that test data */
        buildPreviousMonth(lastOfPreviousMonth, userID);
        buildCurrentMonth(firstOfCurrentMonth, userID);
    }

    protected static void buildPreviousMonth(Timestamp lastOfPreviousMonth, int userID){

        /* data for previous month */

        //[Food] {Home} (4) ~460/510$
        double foodHome1 = 114.98;
        int foodHome1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodHome1);
        addTransaction(foodHome1TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Husband",userID), foodHome1);
        double foodHome2 = 109.26;
        int foodHome2TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodHome2);
        addTransaction(foodHome2TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Husband",userID), foodHome2);
        double foodHome3 = 99.54;
        int foodHome3TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodHome3);
        addTransaction(foodHome3TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Wife",userID), foodHome3);
        double foodHome4 = 122.61;
        int foodHome4TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodHome4);
        addTransaction(foodHome4TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Wife",userID), foodHome4);

        //[Food] {Dining} (2) ~90/110$
        double foodDining1 = 45.95;
        int foodDining1TNX = addReceipt(userID, getLocationID("Restaurant", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodDining1);
        addTransaction(foodDining1TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Dining"), getMemberID("Husband",userID), foodDining1);
        double foodDining2 = 51.11;
        int foodDining2TNX = addReceipt(userID, getLocationID("Restaurant", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodDining2);
        addTransaction(foodDining2TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Dining"), getMemberID("Wife",userID), foodDining2);

        //[Food] {Snacks} (3) ~30/15$
        double foodSnacks1 = 12.84;
        int foodSnacks1TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodSnacks1);
        addTransaction(foodSnacks1TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Snacks"), getMemberID("Husband",userID), foodSnacks1);
        double foodSnacks2 = 6.75;
        int foodSnacks2TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodSnacks2);
        addTransaction(foodSnacks2TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Snacks"), getMemberID("Wife",userID), foodSnacks2);
        double foodSnacks3 = 11.55;
        int foodSnacks3TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), lastOfPreviousMonth, foodSnacks3);
        addTransaction(foodSnacks3TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Snacks"), getMemberID("Husband",userID), foodSnacks3);

        //[Supplies] {Cleaning} (1) ~25/0$
        double suppliesCleaning1 = 8.55;
        int suppliesCleaning1TNX = addReceipt(userID, getLocationID("Bulk Supply", userID), getProcurementID("Expense"), lastOfPreviousMonth, suppliesCleaning1);
        addTransaction(suppliesCleaning1TNX, getCategoryID("Supplies", userID), getSubCategoryID("Supplies", userID, "Cleaning"), getMemberID("Husband",userID), suppliesCleaning1);

        //[Supplies] {Business} (1) ~35/0$
        double suppliesBusiness1 = 32.68;
        int suppliesBusiness1TNX = addReceipt(userID, getLocationID("Bulk Supply", userID), getProcurementID("Expense"), lastOfPreviousMonth, suppliesBusiness1);
        addTransaction(suppliesBusiness1TNX, getCategoryID("Supplies", userID), getSubCategoryID("Supplies", userID, "Business"), getMemberID("Wife",userID), suppliesBusiness1);

        //[Living] {Rent} (1) ~1400/1400$
        double livingRent1 = 1397.42;
        int livingRent1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingRent1);
        addTransaction(livingRent1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Rent"), getMemberID("Husband",userID), livingRent1);

        //[Living] {Insurance} (1) ~90/90$
        double livingInsurance1 = 89.00;
        int livingInsurance1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingInsurance1);
        addTransaction(livingInsurance1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Insurance"), getMemberID("Husband",userID), livingInsurance1);

        //[Living] {Electricity} (1) ~80/100$
        double livingElectricity1 = 78.53;
        int livingElectricity1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingElectricity1);
        addTransaction(livingElectricity1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Electricity"), getMemberID("Husband",userID), livingElectricity1);

        //[Living] {Water} (1) ~30/28$
        double livingWater1 = 28.86;
        int livingWater1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingWater1);
        addTransaction(livingWater1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Water"), getMemberID("Husband",userID), livingWater1);

        //[Living] {Internet} (1) ~75/75$
        double livingInternet1 = 75.00;
        int livingInternet1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingInternet1);
        addTransaction(livingInternet1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Internet"), getMemberID("Husband",userID), livingInternet1);

        //[Living] {Phone} (1) ~115/115$
        double livingPhone1 = 115.21;
        int livingPhone1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingPhone1);
        addTransaction(livingPhone1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Phone"), getMemberID("Husband",userID), livingPhone1);

        //[Living] {Vehicle} (1) ~115/115$
        double livingVehicle1 = 220.81;
        int livingVehicle1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), lastOfPreviousMonth, livingVehicle1);
        addTransaction(livingVehicle1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Vehicle"), getMemberID("Husband",userID), livingVehicle1);

        //[Entertainment] {E-Movie} (2) ~6/0$
        double entertainmentEMovie1 = 2.89;
        int entertainmentEMovie1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Expense"), lastOfPreviousMonth, entertainmentEMovie1);
        addTransaction(entertainmentEMovie1TNX, getCategoryID("Entertainment", userID), getSubCategoryID("Entertainment", userID, "E-Movie"), getMemberID("Wife",userID), entertainmentEMovie1);
        double entertainmentEMovie2 = 2.89;
        int entertainmentEMovie2TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Expense"), lastOfPreviousMonth, entertainmentEMovie2);
        addTransaction(entertainmentEMovie2TNX, getCategoryID("Entertainment", userID), getSubCategoryID("Entertainment", userID, "E-Movie"), getMemberID("Husband",userID), entertainmentEMovie2);

        //[Entertainment] {Game} (1) ~60/0$
        double entertainmentGame1 = 61.31;
        int entertainmentGame1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Expense"), lastOfPreviousMonth, entertainmentGame1);
        addTransaction(entertainmentGame1TNX, getCategoryID("Entertainment", userID), getSubCategoryID("Entertainment", userID, "Game"), getMemberID("Husband",userID), entertainmentGame1);

        //[Entertainment] {Apps} (1) ~3/0$
        double entertainmentApps1 = 3.19;
        int entertainmentApps1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Expense"), lastOfPreviousMonth, entertainmentApps1);
        addTransaction(entertainmentApps1TNX, getCategoryID("Entertainment", userID), getSubCategoryID("Entertainment", userID, "Apps"), getMemberID("Wife",userID), entertainmentApps1);

        //[Shopping] {Clothes} (2) ~110/60$
        double shoppingClothes1 = 64.25;
        int shoppingClothes1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, shoppingClothes1);
        addTransaction(shoppingClothes1TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Clothes"), getMemberID("Wife",userID), shoppingClothes1);
        double shoppingClothes2 = 46.55;
        int shoppingClothes2TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, shoppingClothes2);
        addTransaction(shoppingClothes2TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Clothes"), getMemberID("Wife",userID), shoppingClothes2);

        //[Shopping] {Electronics} (1) ~60/0$
        double shoppingElectronics1 = 52.51;
        int shoppingElectronics1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, shoppingElectronics1);
        addTransaction(shoppingElectronics1TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Electronics"), getMemberID("Husband",userID), shoppingElectronics1);

        //[Shopping] {Shoes} (2) ~90/0$
        double shoppingShoes1 = 47.98;
        int shoppingShoes1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, shoppingShoes1);
        addTransaction(shoppingShoes1TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Shoes"), getMemberID("Wife",userID), shoppingShoes1);
        double shoppingShoes2 = 43.37;
        int shoppingShoes2TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, shoppingShoes2);
        addTransaction(shoppingShoes2TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Shoes"), getMemberID("Husband",userID), shoppingShoes2);

        //[Shopping] {Decor} (1) ~0/100$

        //[Health] {Gym} (1) ~10/10$
        double healthGym1 = 9.99;
        int healthGym1TNX = addReceipt(userID, getLocationID("Gym", userID), getProcurementID("Expense"), lastOfPreviousMonth, healthGym1);
        addTransaction(healthGym1TNX, getCategoryID("Health", userID), getSubCategoryID("Health", userID, "Gym"), getMemberID("Wife",userID), healthGym1);

        //[Health] {Vitamins} (1) ~30/0$
        double healthVitamins1 = 34.59;
        int healthVitamins1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, healthVitamins1);
        addTransaction(healthVitamins1TNX, getCategoryID("Health", userID), getSubCategoryID("Health", userID, "Vitamins"), getMemberID("Wife",userID), healthVitamins1);

        //[Health] {Supplements} (2) ~0/60$

        //[Beauty] {Cosmetics} (1) ~15/15$
        double beautyCosmetics1 = 13.84;
        int beautyCosmetics1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, beautyCosmetics1);
        addTransaction(beautyCosmetics1TNX, getCategoryID("Beauty", userID), getSubCategoryID("Beauty", userID, "Cosmetics"), getMemberID("Wife",userID), beautyCosmetics1);

        //[Beauty] {Luxury} (1) ~0/10$

        //[Beauty] {Spa} (1) ~30/0$
        double beautySpa1 = 30.00;
        int beautySpa1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, beautySpa1);
        addTransaction(beautySpa1TNX, getCategoryID("Beauty", userID), getSubCategoryID("Beauty", userID, "Spa"), getMemberID("Wife",userID), beautySpa1);

        //[Beauty] {Treatments} (1) ~25/0$
        double beautyTreatments1 = 23.98;
        int beautyTreatments1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), lastOfPreviousMonth, beautyTreatments1);
        addTransaction(beautyTreatments1TNX, getCategoryID("Beauty", userID), getSubCategoryID("Beauty", userID, "Treatments"), getMemberID("Wife",userID), beautyTreatments1);

        //[Medicine] {Over-the-Counter} (2) ~60/0$
        double MedicineOTC1 = 27.38;
        int MedicineOTC1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, MedicineOTC1);
        addTransaction(MedicineOTC1TNX, getCategoryID("Medicine", userID), getSubCategoryID("Medicine", userID, "Over-the-Counter"), getMemberID("Wife",userID), MedicineOTC1);
        double MedicineOTC2 = 35.74;
        int MedicineOTC2TNX = addReceipt(userID, getLocationID("Bulk Supply", userID), getProcurementID("Expense"), lastOfPreviousMonth, MedicineOTC2);
        addTransaction(MedicineOTC2TNX, getCategoryID("Medicine", userID), getSubCategoryID("Medicine", userID, "Over-the-Counter"), getMemberID("Husband",userID), MedicineOTC2);

        //[Transportation] {Gas} (2) ~44/51$
        double transportationGas1 = 22.13;
        int transportationGas1TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), lastOfPreviousMonth, transportationGas1);
        addTransaction(transportationGas1TNX, getCategoryID("Transportation", userID), getSubCategoryID("Transportation", userID, "Gas"), getMemberID("Husband",userID), transportationGas1);
        double transportationGas2 = 19.60;
        int transportationGas2TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), lastOfPreviousMonth, transportationGas2);
        addTransaction(transportationGas2TNX, getCategoryID("Transportation", userID), getSubCategoryID("Transportation", userID, "Gas"), getMemberID("Husband",userID), transportationGas2);

        //[Pets] {Food} (2) ~30/30$
        double petsFood1 = 14.32;
        int petsFood1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, petsFood1);
        addTransaction(petsFood1TNX, getCategoryID("Pets", userID), getSubCategoryID("Pets", userID, "Food"), getMemberID("Pet",userID), petsFood1);
        double petsFood2 = 18.43;
        int petsFood2TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), lastOfPreviousMonth, petsFood2);
        addTransaction(petsFood2TNX, getCategoryID("Pets", userID), getSubCategoryID("Pets", userID, "Food"), getMemberID("Pet",userID), petsFood2);

        //[Vacation] {Transportation} (3)~0/120$

    }

    protected static void buildCurrentMonth(Timestamp firstOfCurrentMonth, int userID){

        //DATA OF CURRENT MONTH

        //[Food] {Home} (4) ~460/510$
        double foodHome1 = 124.78;
        int foodHome1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodHome1);
        addTransaction(foodHome1TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Wife",userID), foodHome1);
        double foodHome2 = 101.14;
        int foodHome2TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodHome2);
        addTransaction(foodHome2TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Wife",userID), foodHome2);
        double foodHome3 = 128.39;
        int foodHome3TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodHome3);
        addTransaction(foodHome3TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Wife",userID), foodHome3);
        double foodHome4 = 114.88;
        int foodHome4TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodHome4);
        addTransaction(foodHome4TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Home"), getMemberID("Wife",userID), foodHome4);

        //[Food] {Dining} (2) ~90/110$
        double foodDining1 = 42.15;
        int foodDining1TNX = addReceipt(userID, getLocationID("Restaurant", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodDining1);
        addTransaction(foodDining1TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Dining"), getMemberID("Wife",userID), foodDining1);
        double foodDining2 = 48.19;
        int foodDining2TNX = addReceipt(userID, getLocationID("Restaurant", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodDining2);
        addTransaction(foodDining2TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Dining"), getMemberID("Husband",userID), foodDining2);

        //[Food] {Snacks} (3) ~30/15$
        double foodSnacks1 = 3.94;
        int foodSnacks1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodSnacks1);
        addTransaction(foodSnacks1TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Snacks"), getMemberID("Wife",userID), foodSnacks1);
        double foodSnacks2 = 7.27;
        int foodSnacks2TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodSnacks2);
        addTransaction(foodSnacks2TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Snacks"), getMemberID("Wife",userID), foodSnacks2);
        double foodSnacks3 = 5.95;
        int foodSnacks3TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, foodSnacks3);
        addTransaction(foodSnacks3TNX, getCategoryID("Food", userID), getSubCategoryID("Food", userID, "Snacks"), getMemberID("Wife",userID), foodSnacks3);

        //[Supplies] {Cleaning} (1) ~25/0$

        //[Supplies] {Business} (1) ~35/0$

        //[Living] {Rent} (1) ~1400/1400$
        double livingRent1 = 1389.72;
        int livingRent1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingRent1);
        addTransaction(livingRent1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Rent"), getMemberID("Husband",userID), livingRent1);

        //[Living] {Insurance} (1) ~90/90$
        double livingInsurance1 = 89.00;
        int livingInsurance1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingInsurance1);
        addTransaction(livingInsurance1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Insurance"), getMemberID("Husband",userID), livingInsurance1);

        //[Living] {Electricity} (1) ~80/100$
        double livingElectricity1 = 101.24;
        int livingElectricity1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingElectricity1);
        addTransaction(livingElectricity1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Electricity"), getMemberID("Husband",userID), livingElectricity1);

        //[Living] {Water} (1) ~30/28$
        double livingWater1 = 30.16;
        int livingWater1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingWater1);
        addTransaction(livingWater1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Water"), getMemberID("Husband",userID), livingWater1);

        //[Living] {Internet} (1) ~75/75$
        double livingInternet1 = 75.00;
        int livingInternet1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingInternet1);
        addTransaction(livingInternet1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Internet"), getMemberID("Husband",userID), livingInternet1);

        //[Living] {Phone} (1) ~115/115$
        double livingPhone1 = 112.61;
        int livingPhone1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingPhone1);
        addTransaction(livingPhone1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Phone"), getMemberID("Husband",userID), livingPhone1);

        //[Living] {Vehicle} (1) ~115/115$
        double livingVehicle1 = 220.81;
        int livingVehicle1TNX = addReceipt(userID, getLocationID("Residence", userID), getProcurementID("Bill"), firstOfCurrentMonth, livingVehicle1);
        addTransaction(livingVehicle1TNX, getCategoryID("Living", userID), getSubCategoryID("Living", userID, "Vehicle"), getMemberID("Husband",userID), livingVehicle1);

        //[Entertainment] {E-Movie} (2) ~6/0$

        //[Entertainment] {Game} (1) ~60/0$

        //[Entertainment] {Apps} (1) ~3/0$

        //[Shopping] {Clothes} (2) ~110/60$
        double shoppingClothes1 = 32.15;
        int shoppingClothes1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, shoppingClothes1);
        addTransaction(shoppingClothes1TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Clothes"), getMemberID("Husband",userID), shoppingClothes1);
        double shoppingClothes2 = 29.09;
        int shoppingClothes2TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, shoppingClothes2);
        addTransaction(shoppingClothes2TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Clothes"), getMemberID("Wife",userID), shoppingClothes2);

        //[Shopping] {Electronics} (1) ~60/0$

        //[Shopping] {Shoes} (2) ~90/0$

        //[Shopping] {Decor} (1) ~0/100$
        double shoppingDecor1 = 99.28;
        int shoppingDecor1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, shoppingDecor1);
        addTransaction(shoppingDecor1TNX, getCategoryID("Shopping", userID), getSubCategoryID("Shopping", userID, "Decor"), getMemberID("Wife",userID), shoppingDecor1);

        //[Health] {Gym} (1) ~10/10$
        double healthGym1 = 9.99;
        int healthGym1TNX = addReceipt(userID, getLocationID("Gym", userID), getProcurementID("Expense"), firstOfCurrentMonth, healthGym1);
        addTransaction(healthGym1TNX, getCategoryID("Health", userID), getSubCategoryID("Health", userID, "Gym"), getMemberID("Wife",userID), healthGym1);

        //[Health] {Vitamins} (1) ~30/0$

        //[Health] {Supplements} (2) ~0/60$
        double healthSupplements1 = 34.19;
        int healthSupplements1TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, healthSupplements1);
        addTransaction(healthSupplements1TNX, getCategoryID("Health", userID), getSubCategoryID("Health", userID, "Supplements"), getMemberID("Wife",userID), healthSupplements1);
        double healthSupplements2 = 32.01;
        int healthSupplements2TNX = addReceipt(userID, getLocationID("Mall", userID), getProcurementID("Expense"), firstOfCurrentMonth, healthSupplements2);
        addTransaction(healthSupplements2TNX, getCategoryID("Health", userID), getSubCategoryID("Health", userID, "Supplements"), getMemberID("Wife",userID), healthSupplements2);

        //[Beauty] {Cosmetics} (1) ~15/15$
        double beautyCosmetics1 = 14.34;
        int beautyCosmetics1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, beautyCosmetics1);
        addTransaction(beautyCosmetics1TNX, getCategoryID("Beauty", userID), getSubCategoryID("Beauty", userID, "Cosmetics"), getMemberID("Wife",userID), beautyCosmetics1);

        //[Beauty] {Luxury} (1) ~0/10$
        double beautyLuxury1 = 9.99;
        int beautyLuxury1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, beautyLuxury1);
        addTransaction(beautyLuxury1TNX, getCategoryID("Beauty", userID), getSubCategoryID("Beauty", userID, "Luxury"), getMemberID("Wife",userID), beautyLuxury1);

        //[Beauty] {Spa} (1) ~30/0$

        //[Beauty] {Treatments} (1) ~25/0$

        //[Medicine] {Over-the-Counter} (2) ~60/0$

        //[Transportation] {Gas} (2) ~44/51$
        double transportationGas1 = 25.03;
        int transportationGas1TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), firstOfCurrentMonth, transportationGas1);
        addTransaction(transportationGas1TNX, getCategoryID("Transportation", userID), getSubCategoryID("Transportation", userID, "Gas"), getMemberID("Husband",userID), transportationGas1);
        double transportationGas2 = 28.60;
        int transportationGas2TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), firstOfCurrentMonth, transportationGas2);
        addTransaction(transportationGas2TNX, getCategoryID("Transportation", userID), getSubCategoryID("Transportation", userID, "Gas"), getMemberID("Husband",userID), transportationGas2);

        //[Pets] {Food} (2) ~30/30$
        double petsFood1 = 16.19;
        int petsFood1TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, petsFood1);
        addTransaction(petsFood1TNX, getCategoryID("Pets", userID), getSubCategoryID("Pets", userID, "Food"), getMemberID("Husband",userID), petsFood1);
        double petsFood2 = 15.79;
        int petsFood2TNX = addReceipt(userID, getLocationID("Grocery", userID), getProcurementID("Expense"), firstOfCurrentMonth, petsFood2);
        addTransaction(petsFood2TNX, getCategoryID("Pets", userID), getSubCategoryID("Pets", userID, "Food"), getMemberID("Husband",userID), petsFood2);

        //[Vacation] {Transportation} (3)~0/120$
        double vacationTransportation1 = 41.30;
        int vacationTransportation1TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), firstOfCurrentMonth, vacationTransportation1);
        addTransaction(vacationTransportation1TNX, getCategoryID("Vacation", userID), getSubCategoryID("Vacation", userID, "Transportation"), getMemberID("Husband",userID), vacationTransportation1);
        double vacationTransportation2 = 39.50;
        int vacationTransportation2TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), firstOfCurrentMonth, vacationTransportation2);
        addTransaction(vacationTransportation2TNX, getCategoryID("Vacation", userID), getSubCategoryID("Vacation", userID, "Transportation"), getMemberID("Husband",userID), vacationTransportation2);
        double vacationTransportation3 = 43.91;
        int vacationTransportation3TNX = addReceipt(userID, getLocationID("Gas Station", userID), getProcurementID("Expense"), firstOfCurrentMonth, vacationTransportation3);
        addTransaction(vacationTransportation3TNX, getCategoryID("Vacation", userID), getSubCategoryID("Vacation", userID, "Transportation"), getMemberID("Husband",userID), vacationTransportation3);

    }

    protected static int addReceipt(int userID, int locID, int procID, Timestamp date, double amount) {

        /* storage */
        int receiptID = 9999;

        try{

            /* prepare statement */
            PreparedStatement addTransactionPS = DBConnection.getConnection().prepareStatement(_createReceipt, Statement.RETURN_GENERATED_KEYS);
            addTransactionPS.setInt(1, userID);
            addTransactionPS.setInt(2, locID);
            addTransactionPS.setInt(3, procID);
            addTransactionPS.setTimestamp(4, date);
            addTransactionPS.setDouble(5, amount);
            addTransactionPS.executeUpdate();

            /* result set */
            ResultSet genKeysRS = addTransactionPS.getGeneratedKeys();

            /* loop result set */
            while(genKeysRS.next()){
                /* assign */
                receiptID = genKeysRS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return receiptID;
    }

    protected static void addTransaction(int rcptid, int catID, int subcatID, int memID, double amount) {
        try {

            /* prepare statement */
            PreparedStatement addTransactionToReceipt = DBConnection.getConnection().prepareStatement(_createTransaction);
            addTransactionToReceipt.setInt(1, rcptid);
            addTransactionToReceipt.setInt(2, catID);
            addTransactionToReceipt.setInt(3, subcatID);
            addTransactionToReceipt.setInt(4, memID);
            addTransactionToReceipt.setDouble(5, amount);
            addTransactionToReceipt.executeUpdate();

        }catch(SQLException e) {e.printStackTrace();}
    }

    protected static int getLocationID(String locName, int userID) {

        /* storage */
        int locID = 9999;

        try{

            /* prepare statement */
            PreparedStatement selectLocID = DBConnection.getConnection().prepareStatement(_selectLocationID);
            selectLocID.setInt(1, userID);
            selectLocID.setString(2, locName);

            /* result set */
            ResultSet selectLocID_RS = selectLocID.executeQuery();

            /* loop result set */
            while(selectLocID_RS.next()) {
                /* assign */
                locID = selectLocID_RS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return locID;
    }

    protected static int getProcurementID(String procName) {

        /* storage */
        int procID = 9999;

        try{

            /* prepare statement */
            PreparedStatement selectProcID = DBConnection.getConnection().prepareStatement(_selectProcurementID);
            selectProcID.setString(1, procName);

            /* result set */
            ResultSet selectProcID_RS = selectProcID.executeQuery();

            /* loop result set */
            while(selectProcID_RS.next()) {
                /* assign */
                procID = selectProcID_RS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return procID;
    }

    protected static int getMemberID(String memName, int userID) {

        /* storage */
        int memID = 9999;

        try{

            /* prepare statement */
            PreparedStatement selectMemID = DBConnection.getConnection().prepareStatement(_selectMemberID);
            selectMemID.setInt(1, userID);
            selectMemID.setString(2, memName);

            /* result set */
            ResultSet selectMemID_RS = selectMemID.executeQuery();

            /* loop result set */
            while(selectMemID_RS.next()) {
                /* assign */
                memID = selectMemID_RS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return memID;
    }

    protected static int getCategoryID(String catName,int userID) {

        /* storage */
        int catID = 9999;

        try{

            /* prepare statement */
            PreparedStatement selectCatID = DBConnection.getConnection().prepareStatement(_selectCategoryID);
            selectCatID.setInt(1, userID);
            selectCatID.setString(2, catName);

            /* result set */
            ResultSet selectCatID_RS = selectCatID.executeQuery();

            /* loop result set */
            while(selectCatID_RS.next()) {
                /* assign */
                catID = selectCatID_RS.getInt(1);
            }

        }catch(SQLException e) {e.printStackTrace();}

        return catID;
    }

    protected static int getSubCategoryID(String catName, int userID, String subCatName) {

        /* storage */
        int catID = getCategoryID(catName, userID);
        int subCatID = 9999;

        try{

            /* prepare statement */
            PreparedStatement selectSubCatID = DBConnection.getConnection().prepareStatement(_selectSubCategoryID);
            selectSubCatID.setInt(1, catID);
            selectSubCatID.setString(2, subCatName);

            /* result set */
            ResultSet selectSubCatID_RS = selectSubCatID.executeQuery();

            /* loop result set */
            while(selectSubCatID_RS.next()) {
                /* assign */
                subCatID = selectSubCatID_RS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return subCatID;
    }

    /* storage */
    protected static final String _selectProcurementID = "SELECT proc_id "
            + "FROM procurement "
            + "WHERE proc_name = ?;";
    protected static final String _selectLocationID = "SELECT loc_id "
            + "FROM location "
            + "WHERE user_id = ? AND loc_name = ?;";
    protected static final String _selectMemberID = "SELECT mem_id "
            + "FROM member "
            + "WHERE user_id = ? AND mem_name = ?;";
    protected static final String _selectCategoryID = "SELECT cat_id "
            + "FROM category "
            + "WHERE user_id = ? AND cat_name = ?;";
    protected static final String _selectSubCategoryID = "SELECT subcat_id "
            + "FROM subcategory "
            + "WHERE cat_id = ? AND subcat_name = ?;";
}