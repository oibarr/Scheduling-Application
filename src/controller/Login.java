package controller;

import dao.UserDAO;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class Login implements Initializable {
    @FXML private Label loginLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Button submitButton;
    @FXML private Button exitButton;
    @FXML private Label locationLabel;

    private static final File LOGIN_ACTIVITY = new File("login_activity.txt");
    private static boolean validUsername(String username) throws SQLException {
        return UserDAO.validateUsername(username);
    }

    private static boolean validLogin(String username, String password) throws SQLException{
        return UserDAO.validateLoginCredentials(username, password);
    }

    private boolean loginActivity(boolean loginSuccess){
        String username = usernameTextField.getText();
        String loginOutcome;
        if(loginSuccess){
            loginOutcome = "SUCCESS";
        }else{
            loginOutcome = "FAILED";
        }

        try(FileWriter fileWriter = new FileWriter("login_activity.txt", true)){
            String record = "User: " + username + " [" + loginOutcome + "] " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            System.out.println(record);
            fileWriter.write(record + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginSuccess;
    }

    private boolean validateLogin(String username, String password) throws SQLException {
        return validLogin(username, password);
    }

    private boolean validateInputs() throws SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(username.isEmpty() || username.isBlank()){
            setAlert("Error", "Username cannot be blank");
        } else if (password.isEmpty() || password.isBlank()){
            setAlert("Error", "Password cannot be blank");
        } else if (!validUsername(username)){
            setAlert("Error", "Username does not exist");
        } else if(validUsername(username)){
            if(loginActivity(validateLogin(username, password))){
                System.out.println("User: " + username + " logged in successfully!");
                return true;
            }else {
                setAlert("Error", "Incorrect password");
            }
        }
        return false;
    }

    public void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException {

        if(validateInputs()){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Scheduling Application");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        }

    }

    public void onActionExit(ActionEvent actionEvent) {
        Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to Exit?");

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

        if(Locale.getDefault().getLanguage().equals("fr")){

            resourceBundle = ResourceBundle.getBundle("resourceBundle/language");

            loginLabel.setText(resourceBundle.getString("Login"));
            usernameLabel.setText(resourceBundle.getString("Username"));
            passwordLabel.setText(resourceBundle.getString("Password"));
            submitButton.setText(resourceBundle.getString("Submit"));
            exitButton.setText(resourceBundle.getString("Exit"));
            locationLabel.setText(resourceBundle.getString("Location") + ZoneId.systemDefault());

        }

        //creates "login_activity.txt" file if it does not yet exist or clears it if it does
        createTextFile();

    }
}
