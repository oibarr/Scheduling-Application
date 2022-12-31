package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdbc.JDBC;
import java.util.ResourceBundle;

/**
 * This class creates the GUI-based scheduling application.
 */
public class Main extends Application {

    /** This method sets the GUI. */
    @Override
    public void start(Stage stage) throws Exception {

        //Translates login window to French when Locale is set to "fr"
//        Locale.setDefault(new Locale("fr"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourceBundle/language");

        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle(resourceBundle.getString("Title"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();

    }

    /**
     * This is the main method which launches the application.
     * Scheduling Application Manager 2.0
     * Lambda #1 can be found on line 115 of Login.java
     * Lambda #2 can be found on line 199 of MainMenu.java
     * Javadocs are located in /javadocs
     *
     * @author Osvaldo Ibarra
     */
    public static void main(String[] args) {

        //opens connection with database
        JDBC.openConnection();

        //launches main application
        launch(args);

        //closes connection with database
        JDBC.closeConnection();

    }
}
