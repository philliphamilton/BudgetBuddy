package dao;

import collections.Members;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Member;
import utilities.DBConnection;

/**
 *
 * @author phami13@wgu.edu
 */
public class MemberAccess {

    /* storage */
    protected static final String READ_MEMBERS_BY_USER_ID = "SELECT mem_id, mem_name "
            + "FROM member "
            + "WHERE user_id = ?;";
    private static final String CREATE_MEMBERS_BY_USER_ID = "INSERT INTO member "
            + "(mem_name, user_id) VALUES (?, ?);";
    private static final String  DELETE_MEMBER_BY_ID_USER = "DELETE FROM member "
            + "WHERE mem_id = ? AND user_id = ?;";
    
    public static void populateMembersList(int userid) {

        /* clear list */
        Members.clearMembersList();

        try {

            /* prepare statement */
            PreparedStatement populateList_PS = DBConnection.getConnection().prepareStatement(READ_MEMBERS_BY_USER_ID);
            populateList_PS.setInt(1, userid);

            /* result set */
            ResultSet populateList_RS = populateList_PS.executeQuery();

            /* loop result set */
            while(populateList_RS.next()) {

                /* storage */
                int tempMemID = populateList_RS.getInt(1);
                String tempMemName = populateList_RS.getString(2);
                Member tempMem = new Member(tempMemID, tempMemName);

                /* add to collection */
                Members.getMembersList().add(tempMem);
            }
        }catch(SQLException e) {e.printStackTrace();}
    }
    
    public static void addMember(String memName, int userid) {
        
        try {

            /* prepare statement */
            PreparedStatement addMember_PS = DBConnection.getConnection().prepareStatement(CREATE_MEMBERS_BY_USER_ID, Statement.RETURN_GENERATED_KEYS);
            addMember_PS.setString(1, memName);
            addMember_PS.setInt(2, userid);
            addMember_PS.executeUpdate();

            /* result set */
            ResultSet addMember_GenKeys = addMember_PS.getGeneratedKeys();

            /* loop result set */
            while(addMember_GenKeys.next()) {

                /* storage */
                int tempMemID = addMember_GenKeys.getInt(1);
                Member tempMember = new Member(tempMemID, memName);

                /* add to collection */
                Members.getMembersList().add(tempMember);
            }
        
        }catch(SQLException e) {e.printStackTrace();}
    }

    public static void deleteMember(int memeberID, int userID) {

        try {

            /* prepare statement */
            PreparedStatement deleteMember_PS = DBConnection.getConnection().prepareStatement(DELETE_MEMBER_BY_ID_USER);
            deleteMember_PS.setInt(1, memeberID);
            deleteMember_PS.setInt(2, userID);
            deleteMember_PS.execute();

        }catch(SQLException e) {e.printStackTrace();}
    }
}
