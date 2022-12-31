package controller;

import dao.AppointmentDAO;
import javafx.fxml.Initializable;
import model.Appointment;

/**
 * This class creates the Reports by Location controller which extends from the Reports by Month controller.
 */
public class ReportsByLocation extends ReportsByMonth implements Initializable {
    /** This method filters appointments by location and updates the total count of appointments matching the selected location.
     * @param appointment the appointment */
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

    /** This method initializes the filter with all the appointment locations in the database. */
    @Override
    public void initializeFilter() {

        try {
            appointmentFilter.addAll(AppointmentDAO.getAppointmentLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentFilter);
        System.out.println("Appointment Locations: " + appointmentFilter);
    }
}
