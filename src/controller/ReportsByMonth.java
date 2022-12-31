package controller;

import dao.AppointmentDAO;
import javafx.fxml.Initializable;
import model.Appointment;

/**
 * This class creates the Reports by Month controller which extends from the Reports by Type controller.
 */
public class ReportsByMonth extends ReportsByType implements Initializable {
    /** This method filters appointments by month and updates the total count of appointments matching the selected month.
     * @param appointment the appointment*/
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

    /** This method initializes the filter with all the months that contain appointments. */
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
