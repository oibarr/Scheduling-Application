package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDAO {

    public static ObservableList<Appointment> getAllAppointments() throws Exception {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            allAppointments.add(
                    new Appointment(
                            result.getInt("Appointment_ID"),
                            result.getString("Title"),
                            result.getString("Description"),
                            result.getString("Location"),
                            result.getString("Type"),
                            result.getTimestamp("Start").toLocalDateTime(),
                            result.getTimestamp("End").toLocalDateTime(),
                            result.getInt("Customer_ID"),
                            result.getInt("User_ID"),
                            result.getInt("Contact_ID")
                    )
            );
        }
        return allAppointments;
    }

    public static void addAppointment(String appTitle, String appDesc, String appLoc, String appType, LocalDateTime appStart, LocalDateTime appEnd, int appCustID, int appUserId, int appContId) {
        try {
            String sqlStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setString(1, appTitle);
            preparedStatement.setString(2, appDesc);
            preparedStatement.setString(3, appLoc);
            preparedStatement.setString(4, appType);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appStart));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appEnd));
            preparedStatement.setInt(7, appCustID);
            preparedStatement.setInt(8, appUserId);
            preparedStatement.setInt(9, appContId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modAppointment(int appId, String appTitle, String appDesc, String appLoc, String appType, LocalDateTime appStart, LocalDateTime appEnd, int appCustID, int appUserId, int appContId) {
        try {
            String sqlStatement = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setString(1, appTitle);
            preparedStatement.setString(2, appDesc);
            preparedStatement.setString(3, appLoc);
            preparedStatement.setString(4, appType);
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appStart));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appEnd));
            preparedStatement.setInt(7, appCustID);
            preparedStatement.setInt(8, appUserId);
            preparedStatement.setInt(9, appContId);
            preparedStatement.setInt(10, appId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteAppointment(int appId) {
        try {
            String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setInt(1, appId);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> filterByWeek() throws SQLException{
        ObservableList<Appointment> curWeekApps = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(CURDATE())";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            curWeekApps.add(
                    new Appointment(
                            result.getInt("Appointment_ID"),
                            result.getString("Title"),
                            result.getString("Description"),
                            result.getString("Location"),
                            result.getString("Type"),
                            result.getTimestamp("Start").toLocalDateTime(),
                            result.getTimestamp("End").toLocalDateTime(),
                            result.getInt("Customer_ID"),
                            result.getInt("User_ID"),
                            result.getInt("Contact_ID")
                    )
            );
        }
        return curWeekApps;
    }

    public static ObservableList<Appointment> filterByMonth() throws SQLException{
        ObservableList<Appointment> curMonthApps = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(CURDATE())";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            curMonthApps.add(
                    new Appointment(
                            result.getInt("Appointment_ID"),
                            result.getString("Title"),
                            result.getString("Description"),
                            result.getString("Location"),
                            result.getString("Type"),
                            result.getTimestamp("Start").toLocalDateTime(),
                            result.getTimestamp("End").toLocalDateTime(),
                            result.getInt("Customer_ID"),
                            result.getInt("User_ID"),
                            result.getInt("Contact_ID")
                    )
            );
        }
        return curMonthApps;
    }

    public static ObservableList<Appointment> getAllAppointmentTypes() throws Exception {

        ObservableList<Appointment> allAppointmentTypes = FXCollections.observableArrayList();
        String sqlStatement = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()){
            allAppointmentTypes.add(
                    new Appointment(
                            result.getString("Type")
                    )
            );
        }
        return allAppointmentTypes;
    }

    public static ObservableList<Appointment> getAppointmentLocations() throws Exception {

        ObservableList<Appointment> appointmentLocations = FXCollections.observableArrayList();
        String sqlStatement = "SELECT Title, Location From appointments GROUP BY Location";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()){
            appointmentLocations.add(
                    new Appointment(
                            result.getString("Title"),
                            result.getString("Location")
                    )
            );
        }
        return appointmentLocations;
    }

    public static ObservableList<Appointment> getMonthsFromAppointments() throws Exception {

        ObservableList<Appointment> appointmentMonths = FXCollections.observableArrayList();
        String sqlStatement = "SELECT Title, Start FROM appointments GROUP BY MONTH(Start)";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()){
            appointmentMonths.add(
                    new Appointment(
                            result.getString("Title"),
                            result.getTimestamp("Start").toLocalDateTime()
                    )
            );
        }
        return appointmentMonths;
    }

    public static ObservableList<Appointment> getAppointmentContacts() throws Exception {

        ObservableList<Appointment> appointmentContacts = FXCollections.observableArrayList();
        String sqlStatement = "SELECT contacts.Contact_ID, Contact_Name FROM contacts INNER JOIN appointments ON contacts.Contact_ID = appointments.Contact_ID GROUP BY Contact_Name";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()){
            appointmentContacts.add(
                    new Appointment(
                            result.getInt("Contact_ID"),
                            result.getString("Contact_Name")
                    )
            );
        }
        return appointmentContacts;
    }

    public static ObservableList<Appointment> getAppointmentsByUser(int userId) throws Exception {
        ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM appointments AS a INNER JOIN users AS u ON a.User_ID = u.User_ID WHERE a.User_ID = " + userId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            appointmentsByContact.add(
                    new Appointment(
                            result.getInt("Appointment_ID"),
                            result.getString("Title"),
                            result.getString("Description"),
                            result.getString("Location"),
                            result.getString("Type"),
                            result.getTimestamp("Start").toLocalDateTime(),
                            result.getTimestamp("End").toLocalDateTime(),
                            result.getInt("Customer_ID"),
                            result.getInt("User_ID"),
                            result.getInt("Contact_ID")
                    )
            );
        }
        return appointmentsByContact;
    }
}