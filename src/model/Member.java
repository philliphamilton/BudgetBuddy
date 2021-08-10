package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class Member {

    /* storage */
    private SimpleIntegerProperty _memberID = new SimpleIntegerProperty();
    private SimpleStringProperty _memberName = new SimpleStringProperty();
    
    public Member(int memid, String memname) {
        
        setMemberID(memid);
        setMemberName(memname);
    }

    private void setMemberID(int memid) {
        _memberID.set(memid);
    }

    private void setMemberName(String memname) {
        _memberName.set(memname);
    }
    
    public int getMemeberID() {
        return _memberID.get();
    }
    
    public String getMemberName() {
       return _memberName.get();
    }
    
    public String toString() {
        return getMemberName();
    }

}
