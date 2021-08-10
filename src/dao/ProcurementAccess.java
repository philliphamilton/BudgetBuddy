package dao;

import collections.Procurements;
import utilities.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Procurement;

/**
 *
 * @author phami13@wgu.edu
 */
public class ProcurementAccess {

    /* storage */
    protected static final String READ_PROCUREMENTS = "SELECT proc_id, proc_name "
            + "FROM procurement;";

    public static void populateProcurements() {

        /* clear */
        Procurements.clearProcurementsList();

        try {

            /* prepare statement */
            PreparedStatement populateProcurements_PS = DBConnection.getConnection().prepareStatement(READ_PROCUREMENTS);

            /* result set */
            ResultSet populateProcurements_RS = populateProcurements_PS.executeQuery();

            /* loop result set */
            while(populateProcurements_RS.next()) {

                /* storage */
                int tempProcID = populateProcurements_RS.getInt(1);
                String tempProcName = populateProcurements_RS.getString(2);
                Procurement tempProcurement = new Procurement(tempProcID, tempProcName);

                /* add to collection */
                Procurements.getProcurementsList().add(tempProcurement);
            }
        }catch(SQLException e){e.printStackTrace();}
    }
}
