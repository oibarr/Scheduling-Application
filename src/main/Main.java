package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdbc.JDBC;

import java.util.ResourceBundle;

public class Main extends Application {

//    --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics

    @Override
    public void start(Stage stage) throws Exception {

        //Translates login window to French when Locale is set to "fr"
        //Locale.setDefault(new Locale("fr"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourceBundle/language");


        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        stage.setTitle(resourceBundle.getString("Title"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();

    }

    public static void main(String[] args){

        //opens connection with database
        JDBC.openConnection();

        //launches main application
        launch(args);

        //closes connection with database
        JDBC.closeConnection();

    }
}
