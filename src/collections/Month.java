package collections;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Transaction;

/**
 *
 * @author phami13@wgu.edu
 */
public class Month {
    
    /* collection storage */
    private static ObservableList<Transaction> _transactions = FXCollections.observableArrayList();
    
    /* year month storage */
    private static YearMonth _currentYearMonth;
    private static YearMonth _futureYearMonth;
    private static YearMonth _pastYearMonth;

    /* TIMESTAMP first of current month */
    private static Timestamp _firstOfCurrentMonth;

    /* TIMESTAMP last of current month */
    private static Timestamp _lastOfCurrentMonth;

    /* setters */
    public static void setupYearMonths(YearMonth ym) {
        /* set current year month */
        _currentYearMonth = ym;
        
        /* set min max days of that specific month */
        setMinMaxYearMonth(ym);
        
        /* set future month */
        setFutureMonth(ym);
        
        /* set past month */
        setPastMonth(ym);
    }
    private static void setMinMaxYearMonth(YearMonth currentYearMonth) {
        /* assign min/max values */
        _firstOfCurrentMonth = Timestamp.valueOf(LocalDateTime.of(
                currentYearMonth.getYear(), currentYearMonth.getMonth(), 01, 0, 0));
        _lastOfCurrentMonth = Timestamp.valueOf(LocalDateTime.of(
                currentYearMonth.getYear(), currentYearMonth.getMonth(), currentYearMonth.lengthOfMonth(), 12, 59));
    }
    private static void setFutureMonth(YearMonth ym) {
        /* calculate */
        _futureYearMonth =  ym.plusMonths(1);
    }
    private static void setPastMonth(YearMonth ym) {
        /* calculate */
        _pastYearMonth = ym.minusMonths(1);
    }
    public static void setMonthTransactions(ObservableList<Transaction> tnxs) {
        /* assign observable list */
        _transactions.setAll(tnxs);
    }
    
    /* getters */
    public static YearMonth getCurrentYearMonth() {
        return _currentYearMonth;
    }
    public static YearMonth getFutureYearMonth() {
        return _futureYearMonth;
    }
    public static YearMonth getPastYearMonth() {
        return _pastYearMonth;
    }
    public static Timestamp getFirstOfMonthTimestamp() {
        return _firstOfCurrentMonth;
    }
    public static Timestamp getLastOfMonthTimestamp() {
        return _lastOfCurrentMonth;
    }
    public static ObservableList<Transaction> getTransactionList() {
//        double local = 0.0;
//        for(Transaction tnx : _transactions){
//            local+= tnx.getPaidProperty().doubleValue();
//        }
//        System.out.println("Total Value of List: " + local);
        return _transactions;
    }
    
    /* utilities */
    public static void clearTransactionList(){
        _transactions.clear();
    }

//    public static void printCurrentListTotal() {
//
//        double local = 0.0;
//
//        for(Transaction tnx : _transactions){
//            local+= tnx.getPaidProperty().doubleValue();
//        }
//        System.out.println("Current Value of List: " + local);
//    }

}
