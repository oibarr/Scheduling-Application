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

/**
 * This class creates the login controller.
 */
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
    private static final File LOGIN_ACTIVITY = new File("login_activity.txt");
    public static final ResourceBundle RB = ResourceBundle.getBundle("resourceBundle/language");
    public static final javafx.scene.control.Alert ALERT = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.NONE);

    /**
     * This method sets an alert.
     *
     * @param alertType    the alert type
     * @param alertMessage the alert message
     * @return returns and shows the alert
     */
    public static Optional<ButtonType> setAlert(String alertType, String alertMessage) {
        ALERT.setAlertType(javafx.scene.control.Alert.AlertType.valueOf(alertType.toUpperCase()));
        ALERT.setTitle(RB.getString(alertType) + " " + RB.getString("Dialog"));
        ALERT.setHeaderText(alertType);
        ALERT.setContentText(alertMessage);
        return ALERT.showAndWait();
    }

    /** This method determines whether a username is valid.
     * @param username the username
     * @return returns true if the username is valid in the database */
    private static boolean validUsername(String username) throws SQLException {
        return UserDAO.validateUsername(username);
    }

    /** This method determines whether a login attempt is valid.
     * @param username the username
     * @param password the password
     * @return returns true if the login attempt is valid */
    private static boolean validLogin(String username, String password) throws SQLException {
        return UserDAO.validateLoginCredentials(username, password);
    }

    /** This method logs the login activity and registers whether an attempt passed or failed.
     * @param loginSuccess the login outcome
     * @return returns whether a login attempt passed or failed */
    private boolean loginActivity(boolean loginSuccess) {
        String username = usernameTextField.getText();
        String loginOutcome;

        if (loginSuccess){
            loginOutcome = RB.getString("Success");
        } else {
            loginOutcome = RB.getString("Failed");
        }

        try (FileWriter fileWriter = new FileWriter("login_activity.txt", true)) {
            String record = RB.getString("User") + username + " [" + loginOutcome + "] " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            System.out.println(record);
            fileWriter.write(record + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }

    /**
     * This method checks for an upcoming appointment when a user logs in.
     * Lambda #1: checks associated appointments for an upcoming appointment within 15 minutes of a user's login. The use of a lambda simplifies the code and aids readability.
     */
    private void appointmentCheck() throws Exception {
        User currentUser = UserDAO.getUser(usernameTextField.getText());
        assert currentUser != null;

        ObservableList<Appointment> associatedAppointments = AppointmentDAO.getAppointmentsByUser(currentUser.getUserId());
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

        //Lambda #1
        associatedAppointments.forEach(a -> {
            if (a.getAppStart().isAfter(LocalDateTime.now()) && a.getAppStart().isBefore(LocalDateTime.now().plusMinutes(15)) && upcomingAppointments.isEmpty()){
                upcomingAppointments.add(a);
                setAlert("Information", "You have an upcoming appointment.\nAppointment ID: " + a.getAppId() + " at " + DateTimeFormatter.ofPattern("HH:mm a 'on' E MM-dd-yyyy").format(a.getAppStart()));
            }
        });

        if (upcomingAppointments.isEmpty()){
            setAlert("Information", "You do not have any upcoming appointments.");
        }

    }

    /**
     * This method validates a login attempt.
     *
     * @param username the username
     * @param password the password
     * @return returns true if the login attempt is successful
     */
    private boolean validateLogin(String username, String password) throws SQLException {
        return validLogin(username, password);
    }

    /**
     * This method validates user inputs.
     *
     * @return returns true if user inputs are valid
     */
    private boolean validateInputs() throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || username.isBlank()){
            setAlert("Error", RB.getString("BlankUsername"));
        } else if (password.isEmpty() || password.isBlank()){
            setAlert("Error", RB.getString("BlankPassword"));
        } else if (!validUsername(username)){
            setAlert("Error", RB.getString("IncorrectUsername"));
        } else if (validUsername(username)){
            if (loginActivity(validateLogin(username, password))){
                System.out.println(RB.getString("User") + username + " " + RB.getString("LoggedIn"));
                return true;
            } else {
                setAlert("Error", RB.getString("IncorrectPassword"));
            }
        }
        return false;
    }

    /**
     * This method executes a login attempt.
     *
     * @param actionEvent the action event
     */
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

    /**
     * This method exits the application.
     *
     * @param actionEvent the action event
     */
    public void onActionExit(ActionEvent actionEvent) {
        Optional<ButtonType> result = setAlert("Confirmation", RB.getString("ExitConfirmation"));

        if (result.isPresent() && result.get() == ButtonType.OK){
            JDBC.closeConnection();
            System.exit(0);
        }
    }

    /**
     * This method creates a text file that logs user login activity.
     */
    private void createTextFile() {
        try {
            if (LOGIN_ACTIVITY.createNewFile()){
                System.out.println(LOGIN_ACTIVITY.getName() + " has been created");
            }/* else {
                FileWriter fileWriter = new FileWriter(LOGIN_ACTIVITY.getName());
                System.out.println(LOGIN_ACTIVITY.getName() + " has been reset to default");
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method initializes the login screen.
     * It auto-populates the test user and creates the login_activity.txt file if it does not yet exist.
     */
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

        //creates "login_activity.txt" file if it does not yet exist
        createTextFile();

    }

}
