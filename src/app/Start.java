package app;

import javafx.application.Application;
import javafx.stage.Stage;
import utilities.DBBuild;
import utilities.DBConnection;
import utilities.SceneGrab;

/**
 *
 * @author phami13@wgu.edu
 */
public class Start extends Application {

    public static void main(String[] args) {

        /* create database and connection */
        DBConnection db = new DBConnection();

        /* build the tables for the database "IF EXISTS" */
        DBBuild.buildTables();

        /* main loop */
        launch(args);

        /* closes database after loop exists */
        DBConnection.dropConnection();
    }

    @Override
    public void start(Stage stage) {

        /* login stage from start */
        SceneGrab.grabLoginFromStart(stage);
    }
}
