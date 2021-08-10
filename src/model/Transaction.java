package model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class Transaction {

    /* storage */
    private SimpleIntegerProperty _tnxID = new SimpleIntegerProperty();
    private SimpleIntegerProperty _rcptID = new SimpleIntegerProperty();
    private SimpleStringProperty _location = new SimpleStringProperty();
    private SimpleStringProperty _category = new SimpleStringProperty();
    private SimpleStringProperty _subCategory = new SimpleStringProperty();
    private SimpleDoubleProperty _amount = new SimpleDoubleProperty();
    private SimpleStringProperty _amountString = new SimpleStringProperty();
    private SimpleStringProperty _member = new SimpleStringProperty();
    private SimpleStringProperty _date = new SimpleStringProperty();
    private SimpleStringProperty _procurement = new SimpleStringProperty();

    /* storage */
    DecimalFormat df;
    
    public Transaction(int tnxID, int rcptid, String location, String category, String subcategory, double paid, String member, Timestamp date, String procurement) {
        setTransactionID(tnxID);
        setReceiptID(rcptid);
        setLocation(location);
        setCategory(category);
        setSubCategory(subcategory);
        setPaid(paid);
        setMember(member);
        setDate(date);
        setProcurement(procurement);

    }
    
    private void setTransactionID(int tnxID) {
        _tnxID.set(tnxID);
    }
    
    private void setReceiptID(int rcptID) {
        _rcptID.set(rcptID);
    }
    
    private void setLocation(String location) {
        _location.set(location);
    }

    private void setCategory(String category) {
        _category.set(category);
    }

    private void setSubCategory(String subcategory) {
        _subCategory.set(subcategory);
    }

    private void setPaid(double paid) {
        _amount.set(paid);
        df = new DecimalFormat("#.##");
        _amountString.set(df.format(paid));
    }

    private void setMember(String member) {
        _member.set(member);
    }

    private void setDate(Timestamp date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM-d-yyyy");
        LocalDate simpleDate = date.toLocalDateTime().toLocalDate();
        String tempDate = simpleDate.format(dtf);
        _date.set(tempDate);
    }

    private void setProcurement(String procurement) {
        _procurement.set(procurement);
    }
    
    public SimpleIntegerProperty getTnxIDProperty(){
        return _tnxID;
    }
    public SimpleIntegerProperty getRcptIDProperty(){
        return _rcptID;
    }
    public SimpleStringProperty getLocationProperty(){
        return _location;
    }
    public SimpleStringProperty getCategoryProperty(){
        return _category;
    }
    public SimpleStringProperty getSubCategoryProperty(){
        return _subCategory;
    }
    public SimpleDoubleProperty getPaidProperty(){
        return _amount;
    }
    public SimpleStringProperty getPaidStringProperty(){
        return _amountString;
    }
    public SimpleStringProperty getMemberProperty(){
        return _member;
    }
    public SimpleStringProperty getDateProperty(){
        return _date;
    }
    public SimpleStringProperty getProcurementProperty(){
        return _procurement;
    }
    

    
    
    
}
