package utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author phami13@wgu.edu
 */
public class SceneGrab {

    /* storage */
    protected static Stage primaryStage;

    public static void grabLoginFromStart(Stage stage) {

        try {
            primaryStage = stage;
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Login");
            primaryStage.centerOnScreen();
            primaryStage.getIcons().add(new Image(SceneGrab.class.getResourceAsStream("/views/budgetbuddy_icon_bar_128x128.png")));
            primaryStage.show();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabLogin() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Login");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabNewUser() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_newuser.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - New User");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabMain() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_main.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Main");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabTransaction() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_transaction.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Add Receipt");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabMembers() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_members.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Members");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabLocations() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_locations.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Locations");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabCategories() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_categories.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Categories");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }

    public static void grabReports() {

        try {
            Parent root = FXMLLoader.load(SceneGrab.class.getResource("/views/BB_reports.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("BudgetBuddy - Reports");
            primaryStage.centerOnScreen();

        }catch(IOException ex) {ex.printStackTrace();}
    }
}