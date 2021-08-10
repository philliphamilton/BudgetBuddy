package utilities;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author phami13@wgu.edu
 */
public class DBConnection {

    /* storage */
    private static final String _localAppData = System.getenv("LOCALAPPDATA");
    private static final String _folderName = "BudgetBuddy";
    private static final String _databaseName = "BudgetBuddy.db";
    private static final Path _folderPath = Paths.get(_localAppData
            + "/"
            + _folderName);
    private static final Path _filePath = Paths.get(_localAppData
            + "/"
            + _folderName
            + "/"
            + _databaseName);
    private static final String _connectionString = "jdbc:sqlite:"
            + _localAppData
            + "/"
            + _folderName
            + "/"
            + _databaseName;
    private static Connection _mainConnection = null;

    public DBConnection() {

        makeLocalFolder();
        setConnection();
    }

    private static void setConnection() {

        try {

            /* assign */
            _mainConnection = DriverManager.getConnection(_connectionString);

            /* check truth */
            if(_mainConnection.isValid(30)) {

                /* GOOD CONNECTION */

            }else {
                _mainConnection.close();
                _mainConnection = null;
                _mainConnection = DriverManager.getConnection(_connectionString);
            }

        }catch(SQLException ex) {

            /* alert try again */
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Connection Error");
            a.setHeaderText("Error Establishing Connection");
            a.setContentText("There has been an error getting connection.\nPlease check your Operating System.");

            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                /* exit */
                Platform.exit();
            }
        }
    }

    public static Connection getConnection() {
        return _mainConnection;
    }

    public static void dropConnection() {

        try {

            /* exit */
            getConnection().close();

            if(getConnection().isClosed()) {
                //System.out.println("DB Connection Closed");
            }
        }catch(SQLException ex) {ex.printStackTrace();}
    }

    private static void makeLocalFolder() {

        try {

            /* create directories */
            Files.createDirectories(_folderPath);

        }catch(IOException ex) {

            /* alert try again */
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("File Error");
            a.setHeaderText("Error Creating Local Directory");
            a.setContentText("There has been an error creating Local Directory.\nAttempted location: Users/AppData/Local.\nExiting Application.");

            Optional<ButtonType> result = a.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {

                /* exit */
                Platform.exit();
            }
        }
    }
}
