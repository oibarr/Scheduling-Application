package controller;

import javafx.scene.control.ButtonType;

import java.util.Optional;

public interface Alert {
    javafx.scene.control.Alert ALERT = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.NONE);

    static Optional<ButtonType> setAlert(String alertType, String alertMessage){
        ALERT.setAlertType(javafx.scene.control.Alert.AlertType.valueOf(alertType.toUpperCase()));
        ALERT.setTitle(alertType + " Dialog");
        ALERT.setHeaderText(alertType);
        ALERT.setContentText(alertMessage);
        return ALERT.showAndWait();
    }
}
