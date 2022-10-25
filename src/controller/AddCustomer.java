package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class AddCustomer implements Initializable {
    @FXML private ComboBox<Country> addCustCountry;
    @FXML private ComboBox<Division> addCustDiv;
    @FXML private TextField addCustId;
    @FXML private TextField addCustName;
    @FXML private TextField addCustNum;
    @FXML private TextField addCustAddress;
    @FXML private TextField addCustPost;

    public void onActionCountry (ActionEvent actionEvent) throws SQLException{
        addCustDiv.setItems(DivisionDAO.getCountryDiv(addCustCountry.getValue().getCountryId()));
        addCustDiv.getSelectionModel().selectFirst();
    }

    private boolean validateInputs(){
        String custName = addCustName.getText();
        String custAddress = addCustAddress.getText();
        String custPost = addCustPost.getText();
        String custNum = addCustNum.getText();

        if(custName.isEmpty() || custName.isBlank()){
            setAlert("Error", "Customer must have a name");
        }else if(custAddress.isEmpty() || custAddress.isBlank()){
            setAlert("Error", "Customer must have an address");
        }else if(custPost.isEmpty() || custPost.isBlank()){
            setAlert("Error", "Customer must have a postal code");
        }else if(custNum.isEmpty() || custNum.isBlank()){
            setAlert("Error", "Customer must have a phone number");
        }else {
            Division custDiv = addCustDiv.getValue();
            int custDivId = custDiv.getDivId();

            CustomerDAO.addCustomer(custName, custAddress, custPost, custNum, custDivId);
            return true;
        }
        return false;
    }

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
        addCustId.setDisable(true);

        try {
            addCustCountry.setItems(CountryDAO.getAllCountries());
            addCustCountry.getSelectionModel().selectFirst();

            addCustDiv.setItems(DivisionDAO.getCountryDiv(addCustCountry.getValue().getCountryId()));
            addCustDiv.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}