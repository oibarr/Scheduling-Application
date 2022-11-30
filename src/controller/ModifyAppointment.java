package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import javafx.fxml.Initializable;
import model.Appointment;

import java.sql.SQLException;

public class ModifyAppointment extends AddAppointment implements Initializable {

    //Populates selection from Appointments table
    public void sendApp(Appointment selectedApp) throws SQLException {
        id.setText(String.valueOf(selectedApp.getAppId()));
        title.setText(selectedApp.getAppTitle());
        desc.setText(selectedApp.getAppDesc());
        type.setText(selectedApp.getAppType());
        location.setText(selectedApp.getAppLoc());

        startDate.setValue(selectedApp.getAppStart().toLocalDate());
        startTime.setValue(selectedApp.getAppStart().toLocalTime());
        end.setValue(selectedApp.getAppEnd().toLocalTime());

        customerId.setValue(CustomerDAO.getCustomer(selectedApp.getAppCustId()));
        userId.setValue(UserDAO.getUser(selectedApp.getAppUserId()));
        contactId.setValue(ContactDAO.getContact(selectedApp.getAppContId()));
    }

    //Modifies an appointment
    @Override
    public void saveAppointment(Appointment appointment) {
        AppointmentDAO.modAppointment(
                Integer.parseInt(id.getText()),
                appointment.getAppTitle(),
                appointment.getAppDesc(),
                appointment.getAppType(),
                appointment.getAppLoc(),
                appointment.getAppStart(),
                appointment.getAppEnd(),
                appointment.getAppCustId(),
                appointment.getAppUserId(),
                appointment.getAppContId());
    }

}
