package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsByType extends ReportsDashboard implements Initializable{
    ObservableList<Appointment> appointmentTypes = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentsByType = FXCollections.observableArrayList();
    private void filterByType(Appointment appointment){
        for(Appointment a : allAppointments){
            if(a.getAppType().equals(appointment.getAppType())){
                appointmentsByType.add(a);
            }
        }
    }

    @Override
    public void onActionCombo(ActionEvent actionEvent){
        appointmentsByType.clear();
        filterByType(appCombo.getSelectionModel().getSelectedItem());
        appTable.setItems(appointmentsByType);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        appLabel.setText(appLabel.getText() + " by Type");
        appCombo.setPromptText("Type");

        try {
            appointmentTypes.addAll(AppointmentDAO.getAllAppointmentTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentTypes);
        System.out.println(appointmentTypes);
    }
}
