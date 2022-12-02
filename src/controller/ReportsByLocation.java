package controller;

import dao.AppointmentDAO;
import javafx.fxml.Initializable;
import model.Appointment;

public class ReportsByLocation extends ReportsByMonth implements Initializable {
    @Override
    public void filterSelection(Appointment appointment) {
        int totalCount = 0;
        for (Appointment a : allAppointments) {
            if (a.getAppLoc().equals(appointment.getAppLoc())){
                appointmentsByFilter.add(a);
                totalCount++;
            }
        }
        countLabel.setText("Total Count: " + totalCount);
    }

    @Override
    public void initializeReportScreen() {
        appLabel.setText(appLabel.getText() + " by Location");
        appCombo.setPromptText("Location");

        try {
            appointmentFilter.addAll(AppointmentDAO.getAppointmentLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentFilter);
        System.out.println("Appointment Locations: " + appointmentFilter);
    }

}
