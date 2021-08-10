package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author phami13@wgu.edu
 */
public class User {

    /* storage */
    private SimpleIntegerProperty _userID = new SimpleIntegerProperty();
    private SimpleStringProperty _userName = new SimpleStringProperty();
    private SimpleIntegerProperty _userNew = new SimpleIntegerProperty();

    public User(int userid, String username, int usernew) {
        
        setUserID(userid);
        setUserName(username);
        setUserNew(usernew);
    }

    private void setUserID(int userid) {
        _userID.set(userid);
    }

    private void setUserName(String username) {
        _userName.set(username);
    }
    
    private void setUserNew(int usernew) {
        _userNew.set(usernew);
    }

    public int getUserID() {
        return _userID.get();
    }

    public String getUserName() {
        return _userName.get();
    }
    
    public int getUserNew() {
        return _userNew.get();
    }

    public IntegerProperty userIDProperty() {
        return _userID;
    }

    public StringProperty userNameProperty() {
        return _userName;
    }
    
    public IntegerProperty userNewProperty() {
        return _userNew;
    }

    public User makeUser(int id, String name, int newu) {

        User tempUser = new User(id, name, newu);
        return tempUser;
    }
    
    public String toString() {
        return getUserName();
    }

}
