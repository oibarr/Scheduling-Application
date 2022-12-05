package controller;

import dao.AppointmentDAO;
import javafx.fxml.Initializable;
import model.Appointment;

public class ReportsByMonth extends ReportsByType implements Initializable {
    @Override
    public void filterSelection(Appointment appointment) {
        int totalCount = 0;
        for (Appointment a : allAppointments) {
            if (a.getAppStart().getMonth().equals(appointment.getAppStart().getMonth())){
                appointmentsByFilter.add(a);
                totalCount++;
            }
        }
        countLabel.setText("Total Count: " + totalCount);
    }

    @Override
    public void initializeFilter() {

        try {
            appointmentFilter.addAll(AppointmentDAO.getMonthsFromAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentFilter);
        System.out.println("Appointment Months: " + appointmentFilter);
    }

}
