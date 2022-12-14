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

/**
 * This class creates the Reports by Type controller.
 */
public class ReportsByType implements Initializable {
    protected @FXML TableView<Appointment> appTable;
    protected @FXML Label appLabel;
    protected @FXML Label countLabel;
    protected @FXML TableColumn<Appointment, Integer> Appointment_ID;
    protected @FXML TableColumn<Appointment, String> Title;
    protected @FXML TableColumn<Appointment, String> Description;
    protected @FXML TableColumn<Appointment, String> Location;
    protected @FXML TableColumn<Appointment, String> Type;
    protected @FXML TableColumn<Appointment, Calendar> Start;
    protected @FXML TableColumn<Appointment, Calendar> End;
    protected @FXML TableColumn<Appointment, Integer> App_Customer_ID;
    protected @FXML TableColumn<Appointment, Integer> User_ID;
    protected @FXML TableColumn<Appointment, Integer> Contact_ID;
    protected @FXML ComboBox<Appointment> appCombo;
    protected ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    protected ObservableList<Appointment> appointmentFilter = FXCollections.observableArrayList();
    protected ObservableList<Appointment> appointmentsByFilter = FXCollections.observableArrayList();

    /**
     * This method filters appointments by type and updates the total count of appointments matching the selected type.
     *
     * @param appointment the appointment
     */
    public void filterSelection(Appointment appointment) {

        int totalCount = 0;
        for (Appointment a : allAppointments) {
            if (a.getAppType().equals(appointment.getAppType())){
                appointmentsByFilter.add(a);
                totalCount++;
            }
        }
        countLabel.setText("Total Count: " + totalCount);
    }

    /**
     * This method executes the appointment filter and sets the appointments table with the filtered appointments.
     *
     * @param actionEvent the user selects an appointment type
     */
    public void onActionCombo(ActionEvent actionEvent) {
        appointmentsByFilter.clear();
        filterSelection(appCombo.getSelectionModel().getSelectedItem());
        appTable.setItems(appointmentsByFilter);
    }

    /**
     * This method initializes the filter with all appointment types.
     */
    public void initializeFilter() {
        try {
            appointmentFilter.addAll(AppointmentDAO.getAllAppointmentTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentFilter);
        System.out.println("Appointment Types: " + appointmentFilter);
    }

    /**
     * This method exits the Reports screen and navigates back to the Main Menu.
     *
     * @param actionEvent the user clicks on the Exit button
     */
    public void onActionExit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Scheduling Application");
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method initializes the Reports by Type screen.
     * It displays and counts all the appointments in the database as well as initialize the appointment filter.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        countLabel.setText(countLabel.getText() + allAppointments.size());

        initializeFilter();
    }
}
