package dao;

import collections.Month;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Transaction;
import model.User;
import model.Category;
import model.Member;
import utilities.DBConnection;

/**
 *
 * @author phami13@wgu.edu
 */
public class TransactionsAccess {

    /* storage */
    protected static final String READ_ALL_TRANSACTIONS_BY_RECEPT_MONTH = "SELECT transactions.tnx_id AS TnxID, "
            + "transactions.rcpt_id AS ReceiptID, "
            + "location.loc_name AS Location, "
            + "category.cat_name AS Category, "
            + "subcategory.subcat_name AS SubCategory, "
            + "transactions.amount_paid AS Paid, "
            + "member.mem_name AS Member, "
            + "date AS Date, "
            + "procurement.proc_name AS Procurement "
            + "FROM receipt "
            + "INNER JOIN transactions ON receipt.rcpt_id = transactions.rcpt_id "
            + "INNER JOIN location ON receipt.loc_id = location.loc_id "
            + "INNER JOIN category ON transactions.cat_id = category.cat_id "
            + "INNER JOIN subcategory ON transactions.subcat_id = subcategory.subcat_id "
            + "INNER JOIN member ON transactions.mem_id = member.mem_id "
            + "INNER JOIN procurement ON receipt.proc_id = procurement.proc_id "
            + "WHERE Date "
            + "BETWEEN ? AND ? AND receipt.user_id = ?;";
    protected static final String TOTAL_PAID_BY_MONTH_USER = "SELECT SUM(total_amount_paid) "
            + "FROM receipt "
            + "WHERE date "
            + "BETWEEN ? AND ? AND user_id = ?;";
    protected static final String CREATE_TRANSACTION = "INSERT INTO transactions "
            + "(rcpt_id, cat_id, subcat_id, mem_id, amount_paid) "
            + "VALUES (?, ?, ?, ?, ?);";
    protected static final String READ_TRANSACTIONS_BY_MEM_ID = "SELECT COUNT(mem_id) "
            + "FROM transactions "
            + "WHERE mem_id = ?;";
    protected static final String READ_TRANSACTIONS_BY_CAT_ID = "SELECT COUNT(cat_id) "
            + "FROM transactions "
            + "WHERE cat_id = ?;";
    protected static final String READ_TRANSACTIONS_BY_SUBCAT_ID = "SELECT COUNT(subcat_id) "
            + "FROM transactions "
            + "WHERE subcat_id = ?;";
    protected static final String READ_TRANSACTIONS_BY_RCPT_ID = "SELECT tnx_id "
            + "FROM transactions "
            + "WHERE rcpt_id = ?;";
    protected static final String DELETE_TRANSACTION = "DELETE FROM transactions "
            + "WHERE tnx_id = ?;";
    protected static final String COUNT_TRANSACTIONS = "SELECT COUNT(tnx_id) "
            + "FROM transactions "
            + "WHERE rcpt_id = ?;";
    protected static final String REPORT_SUMMARY_CATEGORY = "SELECT category.cat_id, category.cat_name AS Category, SUM(amount_paid) AS TOTAL "
            + "FROM transactions "
            + "JOIN category ON transactions.cat_id = category.cat_id "
            + "JOIN receipt ON transactions.rcpt_id = receipt.rcpt_id "
            + "WHERE receipt.date BETWEEN ? AND ? AND receipt.user_id = ? "
            + "GROUP BY Category "
            + "ORDER BY TOTAL DESC;";
    protected static final String REPORT_SUMMARY_MEMBER = "SELECT member.mem_id AS MemID, member.mem_name AS Member, SUM(transactions.amount_paid) AS Total "
            + "FROM transactions "
            + "JOIN member ON transactions.mem_id = member.mem_id "
            + "JOIN receipt ON transactions.rcpt_id = receipt.rcpt_id "
            + "WHERE receipt.date BETWEEN ? AND ? AND receipt.user_id = ? "
            + "GROUP BY Member "
            + "ORDER BY Total DESC;";

    public static void populateTransactions(Timestamp firstOfMonthTimestamp, Timestamp lastOfMonthTimestamp, User user) {

        /* clear */
        Month.clearTransactionList();
        
        try {

            /* prepare statement */
            PreparedStatement readBetweenDates_PS = DBConnection.getConnection().prepareStatement(READ_ALL_TRANSACTIONS_BY_RECEPT_MONTH);
            readBetweenDates_PS.setTimestamp(1, firstOfMonthTimestamp);
            readBetweenDates_PS.setTimestamp(2, lastOfMonthTimestamp);
            readBetweenDates_PS.setInt(3, user.getUserID());

            /* result set */
            ResultSet readBetweenDates_RS = readBetweenDates_PS.executeQuery();

            /* loop result set */
            while(readBetweenDates_RS.next()) {

                /* storage */
                int tempTnxID = readBetweenDates_RS.getInt(1);
                int tempRcptID = readBetweenDates_RS.getInt(2);
                String tempLocation = readBetweenDates_RS.getString(3);
                String tempCategory = readBetweenDates_RS.getString(4);
                String tempSubCategory = readBetweenDates_RS.getString(5);
                double tempPaid = readBetweenDates_RS.getDouble(6);
                String tempMember = readBetweenDates_RS.getString(7);
                Timestamp tempDate = readBetweenDates_RS.getTimestamp(8);
                String tempProcurement = readBetweenDates_RS.getString(9);
                Transaction tempReceipt = new Transaction(tempTnxID,
                        tempRcptID,
                        tempLocation, 
                        tempCategory,
                        tempSubCategory, 
                        tempPaid, 
                        tempMember,
                        tempDate, 
                        tempProcurement);

                /* add to collection */
                Month.getTransactionList().add(tempReceipt);
            }
        }catch (SQLException e) {e.printStackTrace();}
    }
    
    public static double totalByMonth(Timestamp firstOfMonthTimestamp, Timestamp lastOfMonthTimestamp, User user) {

        /* storage */
        double tempAmount = 9999;

        try {

            /* prepare statement */
            PreparedStatement totalByMonth_PS = DBConnection.getConnection().prepareStatement(TOTAL_PAID_BY_MONTH_USER);
            totalByMonth_PS.setTimestamp(1, firstOfMonthTimestamp);
            totalByMonth_PS.setTimestamp(2, lastOfMonthTimestamp);
            totalByMonth_PS.setInt(3, user.getUserID());

            /* result set */
            ResultSet totalByMonth_RS = totalByMonth_PS.executeQuery();

            /* loop result set */
            while(totalByMonth_RS.next()) {

                /* assign */
               tempAmount = totalByMonth_RS.getDouble(1);
            }
             
        }catch (SQLException e) {e.printStackTrace();}

        return tempAmount;
    }

    public static void createTransaction(int receiptID, int categoryID, int subCategoryID, int memeberID, Double value) {

        try{

            /* prepare statement */
            PreparedStatement createTransaction_PS = DBConnection.getConnection().prepareStatement(CREATE_TRANSACTION);
            createTransaction_PS.setInt(1, receiptID);
            createTransaction_PS.setInt(2, categoryID);
            createTransaction_PS.setInt(3, subCategoryID);
            createTransaction_PS.setInt(4, memeberID);
            createTransaction_PS.setDouble(5, value);
            createTransaction_PS.executeUpdate();
            
        }catch(SQLException e) {e.printStackTrace();}
    }
    
    public static boolean isMemberInUse(int memeberID) {

        /* storage */
        boolean result = false;
        
        try {

            /* prepare statement */
            PreparedStatement isMemberInUse_PS = DBConnection.getConnection().prepareStatement(READ_TRANSACTIONS_BY_MEM_ID);
            isMemberInUse_PS.setInt(1, memeberID);

            /* result set */
            ResultSet isMemberInUse_RS = isMemberInUse_PS.executeQuery();

            /* loop result set */
            while(isMemberInUse_RS.next()) {

                /* empty check */
                if(isMemberInUse_RS.getInt(1) > 0) {
                    result = true;
                } else{
                    result = false;
                }
            }
        }catch(SQLException e) {e.printStackTrace();}
            
        return result;
    }
    
    public static boolean isCategoryInUse(int catID) {

        /* storage */
        boolean result = false;
        
        try {

            /* prepare statement */
            PreparedStatement isCategoryInUse_PS = DBConnection.getConnection().prepareStatement(READ_TRANSACTIONS_BY_CAT_ID);
            isCategoryInUse_PS.setInt(1, catID);

            /* result set */
            ResultSet isCategoryInUse_RS = isCategoryInUse_PS.executeQuery();

            /* loop result set */
            while(isCategoryInUse_RS.next()) {

                /* empty check */
                if(isCategoryInUse_RS.getInt(1) > 0) {
                    result = true;
                } else{
                    result = false;
                }
            }

        }catch(SQLException e) {e.printStackTrace();}
            
        return result;
    }
    
    public static boolean isSubCategoryInUse(int subcatID) {

        /* storage */
        boolean result = false;
        
        try {

            /* prepare statement */
            PreparedStatement isSubCategoryInUse_PS = DBConnection.getConnection().prepareStatement(READ_TRANSACTIONS_BY_SUBCAT_ID);
            isSubCategoryInUse_PS.setInt(1, subcatID);

            /* result set */
            ResultSet isSubCategoryInUse_RS = isSubCategoryInUse_PS.executeQuery();

            /* loop result set */
            while(isSubCategoryInUse_RS.next()) {

                /* empty check */
                if(isSubCategoryInUse_RS.getInt(1) > 0) {
                    result = true;
                } else{
                    result = false;
                }
            }
            
        }catch(SQLException e) {e.printStackTrace();}
            
        return result;
    }

    public static List<Integer> deleteTransactionIDsByReceiptID(int rcptID) {

        /* storage */
        List<Integer> transactionIDs = new ArrayList<Integer>();

        try{

            /* prepare statement */
            PreparedStatement gatherTnxID_PS = DBConnection.getConnection().prepareStatement(READ_TRANSACTIONS_BY_RCPT_ID);
            gatherTnxID_PS.setInt(1, rcptID);

            /* result set */
            ResultSet gatherTnxID_RS = gatherTnxID_PS.executeQuery();

            /* loop result set */
            while(gatherTnxID_RS.next()) {

                /* add to collection */
                transactionIDs.add(gatherTnxID_RS.getInt(1));
            }

            /* loop collection, delete */
            for(Integer tnxID : transactionIDs) {

                /* prepare statement */
                PreparedStatement deleteAllTnx_PS = DBConnection.getConnection().prepareStatement(DELETE_TRANSACTION);
                deleteAllTnx_PS.setInt(1, tnxID);
                deleteAllTnx_PS.execute();
                deleteAllTnx_PS.close();
            }
        }catch(SQLException e) {e.printStackTrace();}

        return transactionIDs;
    }
    
    public static int countTransactions(int rcptID){

        /* storage */
        int tempInt = 0;
        
        try {

            /* prepare statement */
            PreparedStatement countTransactions_PS = DBConnection.getConnection().prepareStatement(COUNT_TRANSACTIONS);
            countTransactions_PS.setInt(1, rcptID);

            /* result set */
            ResultSet countTransactions_RS = countTransactions_PS.executeQuery();

            /* loop result set */
            while(countTransactions_RS.next()) {

                /* assign */
                tempInt = countTransactions_RS.getInt(1);
            }

        }catch(SQLException e) {e.printStackTrace();}

        return tempInt;
    }

    public static void deleteTransactionID(int tnxID) {

        try{

            /* prepare statement */
            PreparedStatement deleteTransactionID_PS = DBConnection.getConnection().prepareStatement(DELETE_TRANSACTION);
            deleteTransactionID_PS.setInt(1, tnxID);
            deleteTransactionID_PS.execute();
            
        }catch(SQLException e) {e.printStackTrace();}

    }
    
    public static Map<Category, Double> gatherReportCategory(Timestamp firstOfMonth, Timestamp lastOfMonth, int userID) {

        /* storage */
        Map<Category, Double> tempMap = new LinkedHashMap();
        
        try {

            /* prepare statement */
            PreparedStatement gatherReportCategory_PS = DBConnection.getConnection().prepareStatement(REPORT_SUMMARY_CATEGORY);
            gatherReportCategory_PS.setTimestamp(1, firstOfMonth);
            gatherReportCategory_PS.setTimestamp(2, lastOfMonth);
            gatherReportCategory_PS.setInt(3, userID);

            /* result set */
            ResultSet gatherReportCategory_RS = gatherReportCategory_PS.executeQuery();

            /* loop result set */
            while(gatherReportCategory_RS.next()) {

                /* storage */
                Category tempCategory = new Category(gatherReportCategory_RS.getInt(1), gatherReportCategory_RS.getString(2));
                double tempDouble = gatherReportCategory_RS.getDouble(3);

                /* add to collection */
                tempMap.put(tempCategory, tempDouble);
            }
        }catch(SQLException e) {e.printStackTrace();}
        
        return tempMap;
    }

    public static Map<Member, Double> gatherReportMember(Timestamp firstOfMonth, Timestamp lastOfMonth, int userID) {

        /* storage */
        Map<Member, Double> tempMap = new LinkedHashMap();
        
        try {

            /* prepare statement */
            PreparedStatement gatherReportMember_PS = DBConnection.getConnection().prepareStatement(REPORT_SUMMARY_MEMBER);
            gatherReportMember_PS.setTimestamp(1, firstOfMonth);
            gatherReportMember_PS.setTimestamp(2, lastOfMonth);
            gatherReportMember_PS.setInt(3, userID);

            /* result set */
            ResultSet gatherReportMember_RS = gatherReportMember_PS.executeQuery();

            /* loop result set */
            while(gatherReportMember_RS.next()) {

                /* storage */
                Member tempMember = new Member(gatherReportMember_RS.getInt(1), gatherReportMember_RS.getString(2));
                double tempDouble = gatherReportMember_RS.getDouble(3);

                /* add to collection */
                tempMap.put(tempMember, tempDouble);
            }
        
        }catch(SQLException e) {e.printStackTrace();}
        
        return tempMap;
    }
}
