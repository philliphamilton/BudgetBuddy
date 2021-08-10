package utilities;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phami13@wgu.edu
 */
public class DBBuild {

    /* storage */
    private static final String _userTableString = "CREATE TABLE IF NOT EXISTS "
            + "user(user_id integer PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "name text UNIQUE NOT NULL, "
            + "password text NOT NULL, "
            + "new_user integer NOT NULL);";
    private static final String _locationTableString = "CREATE TABLE IF NOT EXISTS "
            + "location(loc_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "loc_name text NOT NULL,"
            + "user_id integer NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES user(user_id));";
    private static final String _procurementTableString = "CREATE TABLE IF NOT EXISTS "
            + "procurement(proc_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "proc_name text NOT NULL);";
    private static final String _memberTableString = "CREATE TABLE IF NOT EXISTS "
            + "member(mem_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "mem_name text NOT NULL,"
            + "user_id integer NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES user(user_id));";
    private static final String _categoryTableString = "CREATE TABLE IF NOT EXISTS "
            + "category(cat_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "cat_name text NOT NULL,"
            + "user_id integer NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES user(user_id));";
    private static final String _subcategoryTableString = "CREATE TABLE IF NOT EXISTS "
            + "subcategory(subcat_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "cat_id integer NOT NULL,"
            + "subcat_name text NOT NULL,"
            + "FOREIGN KEY(cat_id) REFERENCES category(cat_id));";
    private static final String _receiptTableString = "CREATE TABLE IF NOT EXISTS "
            + "receipt(rcpt_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "user_id integer NOT NULL,"
            + "loc_id integer NOT NULL,"
            + "proc_id integer NOT NULL,"
            + "date datetime NOT NULL,"
            + "total_amount_paid real NOT NULL,"
            + "FOREIGN KEY(user_id) REFERENCES user(user_id),"
            + "FOREIGN KEY(loc_id) REFERENCES location(loc_id),"
            + "FOREIGN KEY(proc_id) REFERENCES procurement(proc_id));";
    private static final String _transactionsTableString = "CREATE TABLE IF NOT EXISTS "
            + "transactions(tnx_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "rcpt_id integer NOT NULL,"
            + "cat_id integer NOT NULL,"
            + "subcat_id integer NOT NULL,"
            + "mem_id integer NOT NULL,"
            + "amount_paid real NOT NULL,"
            + "FOREIGN KEY(rcpt_id) REFERENCES receipt(rcpt_id),"
            + "FOREIGN KEY(cat_id) REFERENCES category(cat_id),"
            + "FOREIGN KEY(subcat_id) REFERENCES subcategory(subcat_id),"
            + "FOREIGN KEY(mem_id) REFERENCES member(mem_id));";

    public static void buildTables() {

        try {

            /* statement */
            Statement statement = DBConnection.getConnection().createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate(_userTableString);
            statement.executeUpdate(_procurementTableString);
            /* statement */
            statement.executeUpdate(_locationTableString);
            statement.executeUpdate(_memberTableString);
            /* statement */
            statement.executeUpdate(_categoryTableString);
            statement.executeUpdate(_subcategoryTableString);
            /* statement */
            statement.executeUpdate(_receiptTableString);
            statement.executeUpdate(_transactionsTableString);

            /* default */
            DefaultData.buildProcurements();

        }catch(SQLException e) {

            /* error */
            //e.printStackTrace()
        }

    }

    public static void generateTestData() {

        /* create user */
        TestUser.buildTestUser();

        /* default data */
        DefaultData.buildLocations(TestUser._userID);
        DefaultData.buildMembers(TestUser._userID);
        DefaultData.buildCategories(TestUser._userID);

        /* create user transactions */
        TestUser.buildReceipts(TestUser._userID);

    }
}