package controllers;

import collections.Month;
import dao.ReceiptAccess;
import dao.TransactionsAccess;
import model.Location;
import model.Category;
import model.Member;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.scene.control.ToggleGroup;
import model.Procurement;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class ReportsController {

    /* Non-FXML Storage - currency formatter */
    private static final DecimalFormat decimalFormatterCurrency = new DecimalFormat("#.##");;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radioButtonLocation;

    @FXML
    private ToggleGroup toggleGroupReport;

    @FXML
    private RadioButton radioButtonCategory;

    @FXML
    private RadioButton radioButtonMember;
    
    @FXML
    private RadioButton radioButtonProcurement;

    @FXML
    private Button buttonForward;

    @FXML
    private Label labelMonth;

    @FXML
    private Label labelYear;

    @FXML
    private TextArea reportTextArea;

    @FXML
    private Button buttonReturn;

    @FXML
    void actionBackward(ActionEvent event) {

        /* setup month */
        Month.setupYearMonths(Month.getCurrentYearMonth().minusMonths(1));

        /* view maintenance */
        labelMonth.setText(Month.getCurrentYearMonth().getMonth().name());
        labelYear.setText("" + Month.getCurrentYearMonth().getYear());

        /* populate future report */
        if(radioButtonLocation.isSelected()){
            populateLocationReportForMonth();
        } else if(radioButtonCategory.isSelected()){
            populateCategoryReportForMonth();
        } else if(radioButtonMember.isSelected()){
            populateMemberReportForMonth();
        } else if(radioButtonProcurement.isSelected()){
            populateProcurementReportForMonth();
        }
    }
    
    @FXML
    void actionForward(ActionEvent event) {

        /* setup month */
        Month.setupYearMonths(Month.getCurrentYearMonth().plusMonths(1));

        /* view maintenance */
        labelMonth.setText(Month.getCurrentYearMonth().getMonth().name());
        labelYear.setText("" + Month.getCurrentYearMonth().getYear());

        /* populate future report */
        if(radioButtonLocation.isSelected()){
            populateLocationReportForMonth();
        } else if(radioButtonCategory.isSelected()){
            populateCategoryReportForMonth();
        } else if(radioButtonMember.isSelected()){
            populateMemberReportForMonth();
        } else if(radioButtonProcurement.isSelected()){
            populateProcurementReportForMonth();
        }
    }
    
    @FXML
    void actionLocation(ActionEvent event) {

        /* populate report */
        populateLocationReportForMonth();
    }
    
    @FXML
    void actionCategory(ActionEvent event) {

        /* populate report */
        populateCategoryReportForMonth();
    }

    @FXML
    void actionMember(ActionEvent event) {

        /* populate report */
        populateMemberReportForMonth();
    }
    
    @FXML
    void actionProcurement(ActionEvent event) {

        /* populate report */
        populateProcurementReportForMonth();
    }

    @FXML
    void actionReturn(ActionEvent event) {

        /* change scene */
        SceneGrab.grabMain();
    }

    @FXML
    void initialize() {
        assert radioButtonLocation != null : "fx:id=\"radioButtonLocation\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert toggleGroupReport != null : "fx:id=\"toggleGroupReport\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert radioButtonCategory != null : "fx:id=\"radioButtonCategory\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert radioButtonMember != null : "fx:id=\"radioButtonMember\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert radioButtonProcurement != null : "fx:id=\"radioButtonProcurement\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert buttonForward != null : "fx:id=\"buttonForward\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert labelMonth != null : "fx:id=\"labelMonth\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert labelYear != null : "fx:id=\"labelYear\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert reportTextArea != null : "fx:id=\"reportTextArea\" was not injected: check your FXML file 'BB_reports.fxml'.";
        assert buttonReturn != null : "fx:id=\"buttonReturn\" was not injected: check your FXML file 'BB_reports.fxml'.";

        /* view maintenance */
        labelMonth.setText(Month.getCurrentYearMonth().getMonth().name());
        labelYear.setText("" + Month.getCurrentYearMonth().getYear());
        toggleGroupReport.selectToggle(radioButtonLocation);
        if(radioButtonLocation.isSelected()){
            populateLocationReportForMonth();
        }
    }

    private void populateLocationReportForMonth() {

        /* storage */
        Timestamp firstOfMonth = Month.getFirstOfMonthTimestamp();
        Timestamp lastOfMonth = Month.getLastOfMonthTimestamp();
        int userID = LoginController.getUser().getUserID();
        String stringBuilder = "\n";

        /* collection */
        Map<Location, Double> theMap = new LinkedHashMap<>();

        /* clears, queries, and builds list */
        theMap = ReceiptAccess.gatherReportLocation(firstOfMonth, lastOfMonth, userID);

        /* assign title */
        stringBuilder = stringBuilder.concat("----------- Location Summary For " 
                + Month.getCurrentYearMonth().getMonth().toString()
                + " "
                + String.valueOf(Month.getCurrentYearMonth().getYear())
                + " -----------\n");

        /* loop assignments */
        for( Map.Entry<Location,Double> dub : theMap.entrySet()){
            
            stringBuilder = stringBuilder.concat("\n");
            stringBuilder = stringBuilder.concat( "           "
                    + dub.getKey().getLocationName() 
                    + ": $ "
                    + decimalFormatterCurrency.format(dub.getValue())
                    + "\n");
            stringBuilder = stringBuilder.concat("-------------------------------------------------\n");
        }

        /* view maintenance */
        reportTextArea.clear();
        reportTextArea.setText(stringBuilder);
    }

    private void populateCategoryReportForMonth() {

        /* storage */
        Timestamp firstOfMonth = Month.getFirstOfMonthTimestamp();
        Timestamp lastOfMonth = Month.getLastOfMonthTimestamp();
        int userID = LoginController.getUser().getUserID();
        String stringBuilder = "\n";

        /* collection */
        Map<Category, Double> theMap = new LinkedHashMap<>();

        /* clears, queries, and builds list */
        theMap = TransactionsAccess.gatherReportCategory(firstOfMonth, lastOfMonth, userID);

        /* assign title */
        stringBuilder = stringBuilder.concat("----------- Category Summary For " 
                + Month.getCurrentYearMonth().getMonth().toString()
                + " "
                + String.valueOf(Month.getCurrentYearMonth().getYear())
                + " -----------\n");

        /* loop assignments */
        for( Map.Entry<Category, Double> dub : theMap.entrySet()){
            
            stringBuilder = stringBuilder.concat("\n");
            stringBuilder = stringBuilder.concat( "           "
                    + dub.getKey().getCategoryName()
                    + ": $ "
                    + decimalFormatterCurrency.format(dub.getValue())
                    + "\n");
            stringBuilder = stringBuilder.concat("-------------------------------------------------\n");
        }

        /* view maintenance */
        reportTextArea.clear();
        reportTextArea.setText(stringBuilder);
    }

    private void populateMemberReportForMonth() {

        /* storage */
        Timestamp firstOfMonth = Month.getFirstOfMonthTimestamp();
        Timestamp lastOfMonth = Month.getLastOfMonthTimestamp();
        int userID = LoginController.getUser().getUserID();
        String stringBuilder = "\n";

        /* collection */
        Map<Member, Double> theMap = new LinkedHashMap<>();

        /* clears, queries, and builds list */
        theMap = TransactionsAccess.gatherReportMember(firstOfMonth, lastOfMonth, userID);

        /* assign title */
        stringBuilder = stringBuilder.concat("----------- Member Summary For " 
                + Month.getCurrentYearMonth().getMonth().toString()
                + " "
                + String.valueOf(Month.getCurrentYearMonth().getYear())
                + " -----------\n");

        /* loop assignments */
        for(Map.Entry<Member, Double> dub : theMap.entrySet()){
            
            stringBuilder = stringBuilder.concat("\n");
            stringBuilder = stringBuilder.concat( "           "
                    + dub.getKey().getMemberName()
                    + ": $ "
                    + decimalFormatterCurrency.format(dub.getValue())
                    + "\n");
            stringBuilder = stringBuilder.concat("-------------------------------------------------\n");
        }

        /* view maintenance */
        reportTextArea.clear();
        reportTextArea.setText(stringBuilder);
    }

    private void populateProcurementReportForMonth() {

        /* storage */
        Timestamp firstOfMonth = Month.getFirstOfMonthTimestamp();
        Timestamp lastOfMonth = Month.getLastOfMonthTimestamp();
        int userID = LoginController.getUser().getUserID();
        String stringBuilder = "\n";

        /* collection */
        Map<Procurement, Double> theMap = new LinkedHashMap<>();

        /* clears, queries, and builds list */
        theMap = ReceiptAccess.gatherReportProcurement(firstOfMonth, lastOfMonth, userID);

        /* assign title */
        stringBuilder = stringBuilder.concat("----------- Location Summary For " 
                + Month.getCurrentYearMonth().getMonth().toString()
                + " "
                + String.valueOf(Month.getCurrentYearMonth().getYear())
                + " -----------\n");

        /* loop assignments */
        for( Map.Entry<Procurement,Double> dub : theMap.entrySet()){
            
            stringBuilder = stringBuilder.concat("\n");
            stringBuilder = stringBuilder.concat( "           "
                    + dub.getKey().getProcurementName()
                    + ": $ "
                    + decimalFormatterCurrency.format(dub.getValue())
                    + "\n");
            stringBuilder = stringBuilder.concat("-------------------------------------------------\n");
        }

        /* view maintenance */
        reportTextArea.clear();
        reportTextArea.setText(stringBuilder);
    }
}
