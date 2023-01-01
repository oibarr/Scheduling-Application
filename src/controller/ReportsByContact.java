package controller;

import dao.AppointmentDAO;
import javafx.fxml.Initializable;
import model.Appointment;

/**
 * This class creates the Reports by Contact controller which extends from the Reports by Month controller.
 */
public class ReportsByContact extends ReportsByMonth implements Initializable {
    /** This method filters appointments by contact and updates the total count of appointments matching the selected contact.
     * @param appointment the appointment*/
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

    /** This method initializes the filter with all the contacts in the database. */
    @Override
    public void initializeFilter() {

        try {
            appointmentFilter.addAll(AppointmentDAO.getAppointmentContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appCombo.setItems(appointmentFilter);
        System.out.println("Appointment Contacts: " + appointmentFilter);
    }

}
