package dao;

import collections.Locations;
import utilities.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Location;

/**
 *
 * @author phami13@wgu.edu
 */
public class LocationAccess {

    /* storage */
    protected static final String READ_LOCATIONS_BY_USER_ID = "SELECT loc_id, loc_name "
            + "FROM location "
            + "WHERE user_id = ?;";
    protected static final String CREATE_LOCATIONS_BY_USER_ID = "INSERT INTO location "
            + "(loc_name, user_id) VALUES (?,?);";
    protected static final String DELETE_LOCATION_BY_ID_USER = "DELETE FROM location "
            + "WHERE loc_id = ? AND user_id = ?;";
    
    public static void populateLocationsList(int userid) {

        /* clear list */
        Locations.clearLocationsList();
        

        try {

            /* prepare statement */
            PreparedStatement populateList_PS = DBConnection.getConnection().prepareStatement(READ_LOCATIONS_BY_USER_ID);
            populateList_PS.setInt(1, userid);

            /* result set */
            ResultSet populateList_RS = populateList_PS.executeQuery();

            /* loop result set */
            while(populateList_RS.next()) {

                /* storage */
                int tempLocID = populateList_RS.getInt(1);
                String tempLocName = populateList_RS.getString(2);
                Location tempLoc = new Location(tempLocID, tempLocName);

                /* add to collection */
                Locations.getLocationsList().add(tempLoc);
            }
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void addLocation(String locName, int userid) {
        
        try{

            /* prepare statement */
            PreparedStatement addLocation_PS = DBConnection.getConnection().prepareStatement(CREATE_LOCATIONS_BY_USER_ID, Statement.RETURN_GENERATED_KEYS);
            addLocation_PS.setString(1, locName);
            addLocation_PS.setInt(2, userid);
            addLocation_PS.executeUpdate();

            /* result set */
            ResultSet addLocation_GenKeys = addLocation_PS.getGeneratedKeys();

            /* loop result set */
            while(addLocation_GenKeys.next()) {

                /* storage */
                int tempLocID = addLocation_GenKeys.getInt(1);
                Location tempLocation = new Location(tempLocID,locName);

                /* add to collection */
                Locations.getLocationsList().add(tempLocation);
            }
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void deleteLocation(int locationID, int userID) {
        
        try {

            /* prepare statement */
            PreparedStatement deleteLocation_PS = DBConnection.getConnection().prepareStatement(DELETE_LOCATION_BY_ID_USER);
            deleteLocation_PS.setInt(1, locationID);
            deleteLocation_PS.setInt(2, userID);
            deleteLocation_PS.execute();

        }catch(SQLException e) {e.printStackTrace();}
    }
}
