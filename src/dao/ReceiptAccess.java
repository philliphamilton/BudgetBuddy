package dao;

import java.time.LocalDate;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Location;
import model.Procurement;
import utilities.DBConnection;


/**
 *
 * @author phami13@wgu.edu
 */
public class ReceiptAccess {

    /* storage */
    protected static final String CREATE_RECEIPT = "INSERT INTO receipt "
            + "(user_id, loc_id, proc_id, date, total_amount_paid) "
            + "VALUES (?, ?, ?, ?, ?);";
    protected static final String UPDATE_TOTAL_PAID = "UPDATE receipt "
            + "SET total_amount_paid = ? "
            + "WHERE rcpt_id = ?;";
    protected static final String READ_RECEIPTS_BY_LOC_USER = "SELECT COUNT(loc_id) "
            + "FROM receipt "
            + "WHERE loc_id = ? "
            + "AND user_id = ?;";
    protected static final String DELETE_RECEIPT = "DELETE FROM receipt "
            + "WHERE rcpt_id = ?;";
    protected static final String FIND_TOTAL_PAID = "SELECT total_amount_paid "
            + "FROM receipt "
            + "WHERE rcpt_id = ?;";
    protected static final String REPORT_SUMMARY_LOCATION = "SELECT receipt.loc_id AS LocID, location.loc_name AS Location, SUM(total_amount_paid) AS TOTAL "
            + "FROM receipt JOIN location ON receipt.loc_id = location.loc_id "
            + "WHERE receipt.date BETWEEN ? AND ? AND receipt.user_id = ? "
            + "GROUP BY Location "
            + "ORDER BY TOTAL DESC;";
    protected static final String REPORT_SUMMARY_PROCUREMENT = "SELECT procurement.proc_id AS ProcID, procurement.proc_name AS Procurement, SUM(total_amount_paid) AS Total "
            + "FROM receipt "
            + "JOIN procurement ON receipt.proc_id = procurement.proc_id "
            + "WHERE receipt.date BETWEEN ? AND ? AND receipt.user_id = ? "
            + "GROUP BY Procurement "
            + "ORDER BY Total DESC;";

    public static int createReceipt(int userID, int locationID, int procurementID, LocalDate value, int i) {

        /* storage */
        int rcptID = 99999;
        
        try {

            /* prepare statement */
            PreparedStatement createReceipt_PS = DBConnection.getConnection().prepareStatement(CREATE_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            createReceipt_PS.setInt(1, userID);
            createReceipt_PS.setInt(2, locationID);
            createReceipt_PS.setInt(3, procurementID);
            createReceipt_PS.setTimestamp(4, Timestamp.valueOf(LocalDateTime.of(value, LocalTime.NOON)));
            createReceipt_PS.setInt(5, i);
            createReceipt_PS.executeUpdate();

            /* result set */
            ResultSet createReceipt_RS = createReceipt_PS.getGeneratedKeys();

            /* loop result set */
            while(createReceipt_RS.next()) {

                /* create receit, store generated key */
                rcptID = createReceipt_RS.getInt(1);
            }
        }catch(SQLException e) {e.printStackTrace();}

        return rcptID;
    }

    public static void updateTotalAmountPaid(int rcptID, double totalAmount) {
        
        try {

            /* prepare statement */
            PreparedStatement updateTotalAmountPaid_PS = DBConnection.getConnection().prepareStatement(UPDATE_TOTAL_PAID);
            updateTotalAmountPaid_PS.setDouble(1, totalAmount);
            updateTotalAmountPaid_PS.setInt(2, rcptID);
            updateTotalAmountPaid_PS.executeUpdate();

        }catch(SQLException e) {e.printStackTrace();}
    }

    public static boolean isLocationInUse(int locationID, int userID) {

        /* storage */
        boolean result = false;
        
        try {

            /* prepare statement */
            PreparedStatement isLocationInUse_PS = DBConnection.getConnection().prepareStatement(READ_RECEIPTS_BY_LOC_USER);
            isLocationInUse_PS.setInt(1, locationID);
            isLocationInUse_PS.setInt(2, userID);

            /* result set */
            ResultSet isLocationInUse_RS = isLocationInUse_PS.executeQuery();

            /* loop result set */
            while(isLocationInUse_RS.next()) {

                /* check */
                if(isLocationInUse_RS.getInt(1) > 0) {
                    result = true;
                }else {
                    result = false;
                }
            }
            
        }catch(SQLException e) {e.printStackTrace();}
            
        return result;
    }

    public static void deleteEntireReceipt(int rcptID) {

        /* pre delete maintenance */
        TransactionsAccess.deleteTransactionIDsByReceiptID(rcptID);
        
        try {

            /* prepare statement */
            PreparedStatement deleteOriginalReceipt_PS = DBConnection.getConnection().prepareStatement(DELETE_RECEIPT);
            deleteOriginalReceipt_PS.setInt(1, rcptID);
            deleteOriginalReceipt_PS.execute();

        }catch(SQLException e) {e.printStackTrace();}
    }
    
    public static double findCurrentPaidTotal(int rcptID) {

        /* storage */
        double tempDouble = 9999;
        
        try {

            /* prepare statement */
            PreparedStatement findCurrentPaidTotal_PS = DBConnection.getConnection().prepareStatement(FIND_TOTAL_PAID);
            findCurrentPaidTotal_PS.setInt(1, rcptID);

            /* result set */
            ResultSet findCurrenPaidTotal_RS = findCurrentPaidTotal_PS.executeQuery();

            /* loop result set */
            while(findCurrenPaidTotal_RS.next()) {

                /* assign */
                tempDouble = findCurrenPaidTotal_RS.getDouble(1);
            }
            
        }catch(SQLException e) {e.printStackTrace();}

        return tempDouble;
    }

    public static Map<Location, Double> gatherReportLocation(Timestamp firstOfMonth, Timestamp lastOfMonth, int userID) {

        /* storage */
        Map<Location, Double> tempMap = new LinkedHashMap();
        
        try {

            /* prepare statement */
            PreparedStatement gatherReportLocation_PS = DBConnection.getConnection().prepareStatement(REPORT_SUMMARY_LOCATION);
            gatherReportLocation_PS.setTimestamp(1, firstOfMonth);
            gatherReportLocation_PS.setTimestamp(2, lastOfMonth);
            gatherReportLocation_PS.setInt(3, userID);

            /* result set */
            ResultSet gatherReportLocation_RS = gatherReportLocation_PS.executeQuery();

            /* loop result set */
            while(gatherReportLocation_RS.next()) {

                /* storage */
                Location tempLocation = new Location(gatherReportLocation_RS.getInt(1), gatherReportLocation_RS.getString(2));
                double tempDouble = gatherReportLocation_RS.getDouble(3);

                /* add to collection */
                tempMap.put(tempLocation, tempDouble);
            }

        }catch(SQLException e) {e.printStackTrace();}

        return tempMap;
    }

    public static Map<Procurement, Double> gatherReportProcurement(Timestamp firstOfMonth, Timestamp lastOfMonth, int userID) {

        /* storage */
        Map<Procurement, Double> tempMap = new LinkedHashMap();
        
        try {

            /* prepare statement */
            PreparedStatement gatherReportProcurement_PS = DBConnection.getConnection().prepareStatement(REPORT_SUMMARY_PROCUREMENT);
            gatherReportProcurement_PS.setTimestamp(1, firstOfMonth);
            gatherReportProcurement_PS.setTimestamp(2, lastOfMonth);
            gatherReportProcurement_PS.setInt(3, userID);

            /* result set */
            ResultSet gatherReportProcurement_RS = gatherReportProcurement_PS.executeQuery();

            /* loop result set */
            while(gatherReportProcurement_RS.next()) {

                /* storage */
                Procurement tempProcurement = new Procurement(gatherReportProcurement_RS.getInt(1), gatherReportProcurement_RS.getString(2));
                double tempDouble = gatherReportProcurement_RS.getDouble(3);

                /* add to collection */
                tempMap.put(tempProcurement, tempDouble);
            }
            
        }catch(SQLException e) {e.printStackTrace();}

        return tempMap;
    }
}

    


    
 
    
