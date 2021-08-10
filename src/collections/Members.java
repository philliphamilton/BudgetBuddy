package collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Member;

/**
 *
 * @author phami13@wgu.edu
 */
public class Members {
    
    /* collection storage */
    private static ObservableList<Member> _members = FXCollections.observableArrayList();
    
    /* getter */
    public static ObservableList<Member> getMembersList() {
        return _members;
    }
    
    /* setter */
    public static void setMembers(ObservableList<Member> mems) {
        _members.setAll(mems);
    }
    
    /* utilities */
    public static void clearMembersList() {
        _members.clear();
    }
}
