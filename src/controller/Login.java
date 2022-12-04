package controller;

import dao.AppointmentDAO;
import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdbc.JDBC;
import model.Appointment;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class Login implements Initializable {
    @FXML
    private Label loginLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label locationLabel;
    ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/language");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm MM-dd-yyyy");

    //Create an alert function that is public static final
    //Create a functional interface to set it
    //implement a lambda to call the functional interface

    /*Alert alert = (String alertType, String alertMessage) -> {
        ALERT.setAlertType(javafx.scene.control.Alert.AlertType.valueOf(alertType.toUpperCase()));
        ALERT.setTitle(rb.getString(alertType) + " " + rb.getString("Dialog"));
        ALERT.setHeaderText(alertType);
        ALERT.setContentText(alertMessage);
        return ALERT.showAndWait();
    };*/


    private static final File LOGIN_ACTIVITY = new File("login_activity.txt");

    private static boolean validUsername(String username) throws SQLException {
        return UserDAO.validateUsername(username);
    }

    private static boolean validLogin(String username, String password) throws SQLException {
        return UserDAO.validateLoginCredentials(username, password);
    }

    private boolean loginActivity(boolean loginSuccess){
        String username = usernameTextField.getText();
        String loginOutcome;

        if(loginSuccess){
            loginOutcome = rb.getString("Success");
        }else{
            loginOutcome = rb.getString("Failed");
        }

        try (FileWriter fileWriter = new FileWriter("login_activity.txt", true)) {
            String record = rb.getString("User") + username + " [" + loginOutcome + "] " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            System.out.println(record);
            fileWriter.write(record + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }

    private void appointmentCheck() throws Exception {
        User currentUser = UserDAO.getUser(usernameTextField.getText());
        assert currentUser != null;

        ObservableList<Appointment> associatedAppointments = AppointmentDAO.getAppointmentsByUser(currentUser.getUserId());
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

        //Lambda #1
        associatedAppointments.forEach(a -> {
            if (a.getAppStart().isAfter(LocalDateTime.now()) && a.getAppStart().isBefore(LocalDateTime.now().plusMinutes(15)) && upcomingAppointments.size() == 0){
                upcomingAppointments.add(a);
                setAlert("Information", "You have an upcoming appointment.\nAppointment ID: " + a.getAppId() + " at " + dateTimeFormatter.format(a.getAppStart()));
            }
        });

        if (upcomingAppointments.size() < 1){
            setAlert("Information", "You do not have any upcoming appointments.");
        }
    }

    private boolean validateLogin(String username, String password) throws SQLException {
        return validLogin(username, password);
    }

    private boolean validateInputs() throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || username.isBlank()){
            setAlert("Error", rb.getString("BlankUsername"));
        } else if (password.isEmpty() || password.isBlank()){
            setAlert("Error", rb.getString("BlankPassword"));
        } else if (!validUsername(username)){
            setAlert("Error", rb.getString("IncorrectUsername"));
        } else if(validUsername(username)){
            if(loginActivity(validateLogin(username, password))){
                System.out.println(rb.getString("User") + username + " " + rb.getString("LoggedIn"));
                return true;
            }else {
                setAlert("Error", rb.getString("IncorrectPassword"));
            }
        }
        return false;
    }

    public void onActionLogin(ActionEvent actionEvent) throws Exception {

        if (validateInputs()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Scheduling Application");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
            appointmentCheck();
        }

    }

    public void onActionExit(ActionEvent actionEvent) {
        Optional<ButtonType> result = setAlert("Confirmation", rb.getString("ExitConfirmation"));

        if(result.isPresent() && result.get() == ButtonType.OK){
            JDBC.closeConnection();
            System.exit(0);
        }
    }


    private void clearTextFile() throws IOException {
        try(FileWriter fileWriter = new FileWriter(LOGIN_ACTIVITY)){
            System.out.println(LOGIN_ACTIVITY.getName() + " has been reset to default");
        }
    }

    private void createTextFile(){
        try {
            if(LOGIN_ACTIVITY.createNewFile()){
                System.out.println(LOGIN_ACTIVITY.getName() + " has been created");
            } else {
                clearTextFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locationLabel.setText(locationLabel.getText() + ZoneId.systemDefault());

        //auto-populates test user
        usernameTextField.setText("test");
        passwordTextField.setText("test");

        resourceBundle = ResourceBundle.getBundle("resourceBundle/language");

        loginLabel.setText(resourceBundle.getString("Login"));
        usernameLabel.setText(resourceBundle.getString("Username"));
        passwordLabel.setText(resourceBundle.getString("Password"));
        submitButton.setText(resourceBundle.getString("Submit"));
        exitButton.setText(resourceBundle.getString("Exit"));
        locationLabel.setText(resourceBundle.getString("Location") + ZoneId.systemDefault());

        //creates "login_activity.txt" file if it does not yet exist or clears it if it does
        createTextFile();

    }
}
