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
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class AddAppointment implements Initializable {
    @FXML private TextField addAppId;
    @FXML private TextField addAppTitle;
    @FXML private TextField addAppDesc;
    @FXML private TextField addAppType;
    @FXML private TextField addAppLoc;
    @FXML private DatePicker addAppStartDate;
    @FXML private ComboBox<LocalTime> addAppStartTime;
    @FXML private ComboBox<LocalTime> addAppEnd;
    @FXML private ComboBox<Customer> addAppCustId;
    @FXML private ComboBox<User> addAppUserId;
    @FXML private ComboBox<Contact> addAppCont;
    LocalDate currentDay = LocalDate.now();
    LocalTime currentTime = LocalTime.now();
    ZoneId localZoneId = ZoneId.systemDefault();
    ZoneId _EST = ZoneId.of("America/New_York");

    public void onActionStartDate(ActionEvent actionEvent) {
//        addAppStartTime.setDisable(false);
    }

    public void onActionStartTime(ActionEvent actionEvent) {
//        addAppEnd.setDisable(false);
    }

    public void onActionEnd(ActionEvent actionEvent) {
    }

    public void onActionContact(ActionEvent actionEvent) {
    }

    private boolean validateInputs() throws Exception {
        String appTitle = addAppTitle.getText();
        String appDesc = addAppDesc.getText();
        String appLoc = addAppLoc.getText();
        String appType = addAppType.getText();

        LocalDate appStartDate = addAppStartDate.getValue();
        LocalTime appStartTime = addAppStartTime.getSelectionModel().getSelectedItem();
        LocalDate appEndDate = addAppStartDate.getValue();
        LocalTime appEndTime = addAppEnd.getSelectionModel().getSelectedItem();

        int appCustId = addAppCustId.getValue().getCustId();
        int appUserId = addAppUserId.getValue().getUserId();
        int appContId = addAppCont.getValue().getContId();

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

            AppointmentDAO.addAppointment(appTitle, appDesc, appLoc, appType, appStart, appEnd, appCustId, appUserId, appContId);
            return true;
        }
        return false;
    }

    //Saves appointment; navigates back to MainMenu
    public void onActionSave(ActionEvent actionEvent) {
        try{
            if(validateInputs()){
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Scheduling Application");
                stage.centerOnScreen();
                stage.show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Cancels appointment creation; navigates back to MainMenu
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppId.setDisable(true);
        //addAppUserId.setDisable(true);
        //addAppCustId.setDisable(true);

        //Business hours in EST
        ZonedDateTime startTime = ZonedDateTime.of(currentDay, LocalTime.of(8, 0), _EST);
        ZonedDateTime endTime = ZonedDateTime.of(currentDay, LocalTime.of(22, 0), _EST);

        //Business hours converted to local time
        LocalTime appStartTime = startTime.withZoneSameInstant(localZoneId).toLocalTime();
        LocalTime appEndTime = endTime.withZoneSameInstant(localZoneId).toLocalTime();

        addAppStartDate.setValue(currentDay);

        if(currentTime.isAfter(appEndTime.minusMinutes(15))){
            addAppStartDate.setValue(currentDay.plusDays(1));
        }

        //Creates appointment time slots in 15 minute increments
        while(appStartTime.isBefore(appEndTime.plusNanos(1)) && appStartTime != appEndTime){
            addAppStartTime.getItems().add(appStartTime);
            appStartTime = appStartTime.plusMinutes(15);
            addAppEnd.getItems().add(appStartTime);
        }

        //initializes selections
        addAppStartTime.getSelectionModel().selectFirst();
        addAppEnd.getSelectionModel().selectFirst();

        try {
            addAppUserId.setItems(UserDAO.getAllUsers());
            addAppCustId.setItems(CustomerDAO.getAllCustomers());
            addAppCont.setItems(ContactDAO.getAllContacts());

            addAppUserId.getSelectionModel().selectFirst();
            addAppCustId.getSelectionModel().selectFirst();
            addAppCont.getSelectionModel().selectFirst();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
