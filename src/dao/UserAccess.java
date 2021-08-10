package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utilities.DBConnection;
import utilities.DefaultData;

/**
 *
 * @author phami13@wgu.edu
 */
public class UserAccess {

    /* storage */
    protected static boolean _didPassSecurity;
    protected static final String READ_USER = "SELECT name "
            + "FROM user;";
    protected static final String READ_USER_BY_NAME = "SELECT user_id " 
            + "FROM user "
            + "WHERE name = ?;";
    protected static final String READ_USER_PASS_BY_ID = "SELECT password "
            + "FROM user "
            + "WHERE user_id = ?";
    protected static final String READ_NEW_USER_BY_NAME = "SELECT new_user "
            + "FROM user "
            + "WHERE name = ?;";
    protected static final String CREATE_USER = "INSERT INTO user"
            + "(name, password, new_user) "
            + "VALUES(?,?,?);";

    public static void createUser(String name, String pass, boolean isDefaultCategories, boolean isDefaultLocations, boolean isDefaultMembers){

        /* storage */
        int freshUserID = 9999;
        
        try {

            /* prepare statement */
            PreparedStatement createUserPS = DBConnection.getConnection().prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            createUserPS.setString(1, name);
            createUserPS.setString(2, pass);
            createUserPS.setInt(3, 1);
            createUserPS.executeUpdate();

            /* result set */
            ResultSet genKeysRS = createUserPS.getGeneratedKeys();

            /* loop result set */
            while(genKeysRS.next()) {

                /* assign */
                freshUserID = genKeysRS.getInt(1);
            }

            /* check truth */
            if(isDefaultCategories) {

                /* build default  */
                DefaultData.buildCategories(freshUserID);
            }
            if(isDefaultLocations) {

                /* build default  */
                DefaultData.buildLocations(freshUserID);
            }
            if(isDefaultMembers) {

                /* build default  */
                DefaultData.buildMembers(freshUserID);
            }
            
        }catch(SQLException e) {e.printStackTrace();}
        
    }

    public static boolean doesUserExist(String name){

        /* storage */
        boolean doesUserExist = false;

        try {

            /* prepare statement */
            PreparedStatement readUserPS = DBConnection.getConnection().prepareStatement(READ_USER_BY_NAME);
            readUserPS.setString(1, name);

            /* result set */
            ResultSet readUserRS = readUserPS.executeQuery();

            /* loop result set */
            while(readUserRS.next()) {

                /* assign */
                doesUserExist = true;
            }
        }catch(SQLException e) {e.printStackTrace();}

        return doesUserExist;
    }

    public static int getUserID(String name) {

        /* storage */
        int userID = 9999;
        
        try {

            /* prepare statement */
            PreparedStatement readUserID_PS = DBConnection.getConnection().prepareStatement(READ_USER_BY_NAME);
            readUserID_PS.setString(1, name);

            /* result set */
            ResultSet readUserID_RS = readUserID_PS.executeQuery();

            /* loop result set */
            while(readUserID_RS.next()) {

                /* assign */
                userID = readUserID_RS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return userID;
    }

    public static int getNewUser(String name) {

        /* storage */
        int newUser = 9999;

        try {

            /* prepare statement */
            PreparedStatement getNewUser_PS = DBConnection.getConnection().prepareStatement(READ_NEW_USER_BY_NAME);
            getNewUser_PS.setString(1, name);

            /* result set */
            ResultSet getNewUser_RS = getNewUser_PS.executeQuery();

            /* loop result set */
            while(getNewUser_RS.next()) {

                /* assign */
                newUser = getNewUser_RS.getInt(1);
            }
            
        }catch(SQLException e) {e.printStackTrace();}
        
        return newUser;
    }

    public static void securityReadUser(String name, String pass) {

        /* setup storage */
        setPassSecurity(false);

        try {

            /* prepare statement */
            PreparedStatement userPS = DBConnection.getConnection().prepareStatement(READ_USER_BY_NAME);
            userPS.setString(1, name);

            /* result set */
            ResultSet userRS = userPS.executeQuery();

            /* loop result set */
            while(userRS.next()) {

                /* prepare statement */
                PreparedStatement passPS = DBConnection.getConnection().prepareStatement(READ_USER_PASS_BY_ID);
                passPS.setInt(1, userRS.getInt(1));

                /* result set */
                ResultSet passRS = passPS.executeQuery();

                /* loop result set */
                while(passRS.next()) {

                    /* check truth */
                    if(passRS.getString(1).equals(pass)) {
                        setPassSecurity(true);
                    }
                    else{
                        setPassSecurity(false);
                    }
                }
            }
        }catch(SQLException e) {e.printStackTrace();}

    }

    private static void setPassSecurity(boolean b) {

        /* assign */
        _didPassSecurity = b;
    }
    
    public static boolean getPassSecurity() {

        return _didPassSecurity;
    }
}
