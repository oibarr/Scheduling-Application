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
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class AddAppointment implements Initializable {

    @FXML
    protected TextField id;
    @FXML
    protected TextField title;
    @FXML
    protected TextField desc;
    @FXML
    protected TextField type;
    @FXML
    protected TextField location;
    @FXML
    protected DatePicker startDate;
    @FXML
    protected ComboBox<LocalTime> startTime;
    @FXML
    protected ComboBox<LocalTime> end;
    @FXML
    protected ComboBox<Customer> customerId;
    @FXML
    protected ComboBox<User> userId;
    @FXML
    protected ComboBox<Contact> contactId;

    //Time conversion
    protected LocalDate currentDay = LocalDate.now();
    protected LocalTime currentTime = LocalTime.now();
    protected ZoneId localZoneId = ZoneId.systemDefault();
    protected ZoneId _EST = ZoneId.of("America/New_York");

    //Input validation
    boolean validateInputs() throws Exception {
        String appTitle = title.getText();
        String appDesc = desc.getText();
        String appLoc = location.getText();
        String appType = type.getText();

        LocalDate appStartDate = startDate.getValue();
        LocalTime appStartTime = startTime.getSelectionModel().getSelectedItem();
        LocalDate appEndDate = startDate.getValue();
        LocalTime appEndTime = end.getSelectionModel().getSelectedItem();

        int appCustId = customerId.getValue().getCustId();
        int appUserId = userId.getValue().getUserId();
        int appContId = contactId.getValue().getContId();

        if (appTitle.isEmpty() || appTitle.isBlank()){
            setAlert("Error", "Appointment must have a title");

        } else if (appDesc.isEmpty() || appDesc.isBlank()){
            setAlert("Error", "Appointment must have a description");

        } else if (appType.isEmpty() || appType.isBlank()){
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

        }else if (AppointmentDAO.appOverlapCheck(appCustId, 0, appStartDate, appStartTime, appEndDate, appEndTime)){
            setAlert("Error", "Appointment overlaps with existing customer appointments");

        } else {
            LocalDateTime appStart = LocalDateTime.of(appStartDate, appStartTime);
            LocalDateTime appEnd = LocalDateTime.of(appEndDate, appEndTime);

            saveAppointment(new Appointment(appTitle, appDesc, appLoc, appType, appStart, appEnd, appCustId, appUserId, appContId));
            return true;
        }
        return false;
    }

    //Creates an appointment
    public void saveAppointment(Appointment appointment) {
        AppointmentDAO.addAppointment(
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

    //Saves appointment; navigates back to MainMenu
    public void onActionSave(ActionEvent actionEvent) {
        try {
            if (validateInputs()){
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
        id.setDisable(true);

        //Business hours in EST
        ZonedDateTime zonedStartTime = ZonedDateTime.of(currentDay, LocalTime.of(8, 0), _EST);
        ZonedDateTime zonedEndTime = ZonedDateTime.of(currentDay, LocalTime.of(22, 0), _EST);

        //Business hours converted to local time
        LocalTime appStartLocalTime = zonedStartTime.withZoneSameInstant(localZoneId).toLocalTime();
        LocalTime appEndLocalTime = zonedEndTime.withZoneSameInstant(localZoneId).toLocalTime();

        startDate.setValue(currentDay);

        if (currentTime.isAfter(appEndLocalTime.minusMinutes(15))){
            startDate.setValue(currentDay.plusDays(1));
        }

        //Creates appointment time slots in 15 minute increments
        while (appStartLocalTime.isBefore(appEndLocalTime.plusNanos(1)) && appStartLocalTime != appEndLocalTime) {
            startTime.getItems().add(appStartLocalTime);
            appStartLocalTime = appStartLocalTime.plusMinutes(15);
            end.getItems().add(appStartLocalTime);
        }

        //initializes selections
        startTime.getSelectionModel().selectFirst();
        end.getSelectionModel().selectFirst();

        try {
            userId.setItems(UserDAO.getAllUsers());
            customerId.setItems(CustomerDAO.getAllCustomers());
            contactId.setItems(ContactDAO.getAllContacts());

            userId.getSelectionModel().selectFirst();
            customerId.getSelectionModel().selectFirst();
            contactId.getSelectionModel().selectFirst();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
