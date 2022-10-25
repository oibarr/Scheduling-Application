package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsByLocation extends ReportsDashboard implements Initializable {
    ObservableList<Appointment> appointmentLocations = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentsByLocation = FXCollections.observableArrayList();

    private void filterByLocation(Appointment appointment){
        for(Appointment a : allAppointments){
            if(a.getAppLoc().equals(appointment.getAppLoc())){
                appointmentsByLocation.add(a);
            }
        }
    }

    @Override
    public void onActionCombo(ActionEvent actionEvent){
        appointmentsByLocation.clear();
        filterByLocation(appCombo.getSelectionModel().getSelectedItem());
        appTable.setItems(appointmentsByLocation);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        appLabel.setText(appLabel.getText() + " by Location");
        appCombo.setPromptText("Location");

        try {
            appointmentLocations.addAll(AppointmentDAO.getAppointmentLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentLocations);
        System.out.println("Appointment Locations: " + appointmentLocations);
    }
}
