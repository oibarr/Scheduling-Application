package controller;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsByContact extends ReportsDashboard implements Initializable {

    ObservableList<Appointment> appointmentContacts = FXCollections.observableArrayList();
    ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();

    private void filterByContact(int contID){
        for(Appointment a : allAppointments){
            if(a.getAppContId() == contID){
                appointmentsByContact.add(a);
            }
        }
    }

    @Override
    public void onActionCombo(ActionEvent actionEvent) throws Exception {
        appointmentsByContact.clear();
        filterByContact(appCombo.getSelectionModel().getSelectedItem().getAppContId());
        appTable.setItems(appointmentsByContact);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        appLabel.setText(appLabel.getText() + " by Contact");
        appCombo.setPromptText("Contact");

        try {
            appointmentContacts.addAll(AppointmentDAO.getAppointmentContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentContacts);
        System.out.println(appointmentContacts);

    }
}
