package controller;

import dao.AppointmentDAO;
import javafx.fxml.Initializable;
import model.Appointment;

public class ReportsByContact extends ReportsByMonth implements Initializable {
    @Override
    public void filterSelection(Appointment appointment) {
        int totalCount = 0;
        for (Appointment a : allAppointments) {
            if (a.getAppContId() == appointment.getAppContId()){
                appointmentsByFilter.add(a);
                totalCount++;
            }
        }
        countLabel.setText("Total Count: " + totalCount);
    }

    @Override
    public void initializeReportScreen() {
        appLabel.setText(appLabel.getText() + " by Contact");
        appCombo.setPromptText("Contact");

        try {
            appointmentFilter.addAll(AppointmentDAO.getAppointmentContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentFilter);
        System.out.println("Appointment Contacts: " + appointmentFilter);
    }

}
