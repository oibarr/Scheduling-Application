package controller;

import dao.AppointmentDAO;
import dao.ContactDAO;
import dao.CustomerDAO;
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class ModifyAppointment implements Initializable {

    @FXML private TextField modAppId;
    @FXML private TextField modAppTitle;
    @FXML private TextField modAppDesc;
    @FXML private TextField modAppType;
    @FXML private TextField modAppLoc;
    @FXML private DatePicker modAppStartDate;
    @FXML private ComboBox<LocalTime> modAppStartTime;
    @FXML private ComboBox<LocalTime> modAppEnd;
    @FXML private ComboBox<Customer> modAppCustId;
    @FXML private ComboBox<User> modAppUserId;
    @FXML private ComboBox<Contact> modAppCont;
    LocalDate currentDay = LocalDate.now();
//    LocalTime currentTime = LocalTime.now();
    ZoneId localZoneId = ZoneId.systemDefault();
    ZoneId _EST = ZoneId.of("America/New_York");
    public void onActionStartDate(ActionEvent actionEvent) {
        modAppStartTime.setDisable(false);
    }

    public void onActionStartTime(ActionEvent actionEvent) {
        modAppEnd.setDisable(false);
    }

    public void onActionEnd(ActionEvent actionEvent) {
    }

    public void onActionContact(ActionEvent actionEvent) {
    }

    private boolean validateInputs() throws Exception {
        int appId = Integer.parseInt(modAppId.getText());
        String appTitle = modAppTitle.getText();
        String appDesc = modAppDesc.getText();
        String appLoc = modAppLoc.getText();
        String appType = modAppType.getText();

        LocalDate appStartDate = modAppStartDate.getValue();
        LocalTime appStartTime = modAppStartTime.getSelectionModel().getSelectedItem();
        LocalDate appEndDate = modAppStartDate.getValue();
        LocalTime appEndTime = modAppEnd.getSelectionModel().getSelectedItem();

        int appCustId = modAppCustId.getValue().getCustId();
        int appUserId = modAppUserId.getValue().getUserId();
        int appContId = modAppCont.getValue().getContId();

        if(appTitle.isEmpty() || appTitle.isBlank()) {
            setAlert("Error", "Appointment must have a title");

        }else if(appDesc.isEmpty() || appDesc.isBlank()) {
            setAlert("Error", "Appointment must have a description");

        }else if(appType.isEmpty() || appType.isBlank()) {
            setAlert("Error", "Appointment must have a type");

        }else if(appLoc.isEmpty() || appLoc.isBlank()) {
            setAlert("Error", "Appointment must have a location");

        }else if(appStartDate == null) {
            setAlert("Error", "Appointment must have a valid start date");

        }else if(appStartTime == null) {
            setAlert("Error", "Appointment must have a valid start time");

        }else if(appEndDate == null) {
            setAlert("Error", "Appointment must have a valid end date");

        }else if(appEndTime == null) {
            setAlert("Error", "Appointment must have a valid end time");

        }/*else if(appStartDate.isBefore(currentDay)){
                setAlert("Error", "Appointment start date cannot happen before today's date");

        }else if(appStartTime.isBefore(currentTime)) {
                setAlert("Error", "Appointment start time cannot occur before current time");

        }*/else if(appStartTime.isAfter(appEndTime)) {
            setAlert("Error", "Appointment start time cannot be after appointment end time");

        }else if(appEndTime.isBefore(appStartTime)) {
            setAlert("Error", "Appointment end time cannot be before appointment start time");

        }else if(appStartTime.equals(appEndTime)) {
            setAlert("Error", "Appointment cannot start and end at the same time");

        }else if(AppointmentDAO.appOverlapCheck(appCustId, 0, appStartDate, appStartTime, appEndDate, appEndTime)) {
            setAlert("Error", "Appointment overlaps with existing customer appointments");

        }else {
            LocalDateTime appStart = LocalDateTime.of(appStartDate, appStartTime);
            LocalDateTime appEnd = LocalDateTime.of(appEndDate, appEndTime);

            AppointmentDAO.modAppointment(appId, appTitle, appDesc, appLoc, appType, appStart, appEnd, appCustId, appUserId, appContId);
            return true;
        }
        return false;
    }
    public void onActionSave(ActionEvent actionEvent) {
        try {
            if(validateInputs()){
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Scheduling Application");
                stage.centerOnScreen();
                stage.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to cancel without saving?");

        if(result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Scheduling Application");
            stage.centerOnScreen();
            stage.show();
        }
    }


    public void sendApp(Appointment selectedApp) throws SQLException {
        modAppId.setText(String.valueOf(selectedApp.getAppId()));
        modAppTitle.setText(selectedApp.getAppTitle());
        modAppDesc.setText(selectedApp.getAppDesc());
        modAppType.setText(selectedApp.getAppType());
        modAppLoc.setText(selectedApp.getAppLoc());

        modAppStartDate.setValue(selectedApp.getAppStart().toLocalDate());
        modAppStartTime.setValue(selectedApp.getAppStart().toLocalTime());
        modAppEnd.setValue(selectedApp.getAppEnd().toLocalTime());

        modAppCustId.setValue(CustomerDAO.getCustomer(selectedApp.getAppCustId()));
        modAppUserId.setValue(UserDAO.getUser(selectedApp.getAppUserId()));
        modAppCont.setValue(ContactDAO.getContact(selectedApp.getAppContId()));
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modAppId.setDisable(true);
        //modAppStartDate.setDisable(true);
        //modAppUserId.setDisable(true);
        //modAppCustId.setDisable(true);

        //implement time conversion
        //First: Start with a localDateTime
        //Second: Then, Convert to a ZonedDateTime at the ORIGIN UTC ZoneId  (using .atZone())
        //Third: Convert the created ZonedDateTime at the TARGET systemDefault ZoneId (using .withZoneSameInstant() and passing the TARGET ZoneId using ZoneId.of())
        //Finally: Convert the Target ZonedDateTime BACK into a LocalDateTime

        ZonedDateTime startTime = ZonedDateTime.of(currentDay, LocalTime.of(8, 0), _EST);
        ZonedDateTime endTime = ZonedDateTime.of(currentDay, LocalTime.of(22, 0), _EST);

        LocalTime appStartTime = startTime.withZoneSameInstant(localZoneId).toLocalTime();
        LocalTime appEndTime = endTime.withZoneSameInstant(localZoneId).toLocalTime();

        while(appStartTime.isBefore(appEndTime.plusNanos(1)) && appStartTime != appEndTime){
            modAppStartTime.getItems().add(appStartTime);
            appStartTime = appStartTime.plusMinutes(15);
            modAppEnd.getItems().add(appStartTime);
        }

        try {
            modAppUserId.setItems(UserDAO.getAllUsers());
            modAppCustId.setItems(CustomerDAO.getAllCustomers());
            modAppCont.setItems(ContactDAO.getAllContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
