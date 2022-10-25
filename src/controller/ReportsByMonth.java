package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsByMonth extends ReportsDashboard implements Initializable {
    ObservableList<Appointment> appointmentMonths = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentsByMonth = FXCollections.observableArrayList();
    private void filterByMonth(Appointment appointment){
        for(Appointment a : allAppointments){
            if(a.getAppStart().getMonth().equals(appointment.getAppStart().getMonth())){
                appointmentsByMonth.add(a);
            }
        }
    }

    @Override
    public void onActionCombo(ActionEvent actionEvent){
        appointmentsByMonth.clear();
        filterByMonth(appCombo.getSelectionModel().getSelectedItem());
        appTable.setItems(appointmentsByMonth);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        appLabel.setText(appLabel.getText() + " by Month");
        appCombo.setPromptText("Month");

        try {
            appointmentMonths.addAll(AppointmentDAO.getMonthsFromAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentMonths);
        System.out.println("Appointment Months: " + appointmentMonths);

    }
}
