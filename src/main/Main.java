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

//        Locale.setDefault(new Locale("fr"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resourceBundle/language");


        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
//        stage.setTitle("Scheduling Application");
        stage.setTitle(resourceBundle.getString("Title"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();

    }

    public static void main(String[] args){

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();

    }
}
