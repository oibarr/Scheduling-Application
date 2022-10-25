package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ReportsDashboard implements Initializable{

    @FXML TableView<Appointment> appTable;
    @FXML Label appLabel;
    @FXML TableColumn<Appointment, Integer> Appointment_ID;
    @FXML TableColumn<Appointment, String> Title;
    @FXML TableColumn<Appointment, String> Description;
    @FXML TableColumn<Appointment, String> Location;
    @FXML TableColumn<Appointment, String> Type;
    @FXML TableColumn<Appointment, Calendar> Start;
    @FXML TableColumn<Appointment, Calendar> End;
    @FXML TableColumn<Appointment, Integer> App_Customer_ID;
    @FXML TableColumn<Appointment, Integer> User_ID;
    @FXML TableColumn<Appointment, Integer> Contact_ID;
    @FXML ComboBox<Appointment> appCombo;
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public void onActionCombo(ActionEvent actionEvent) throws Exception {
    }

    public void onActionExit(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Scheduling Application");
            stage.centerOnScreen();
            stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        appCombo.setPromptText("");

        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        Location.setCellValueFactory(new PropertyValueFactory<>("appLoc"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        App_Customer_ID.setCellValueFactory(new PropertyValueFactory<>("appCustId"));
        User_ID.setCellValueFactory(new PropertyValueFactory<>("appUserId"));
        Contact_ID.setCellValueFactory(new PropertyValueFactory<>("appContId"));

        try {
            allAppointments.addAll(AppointmentDAO.getAllAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appTable.setItems(allAppointments);
    }

}
