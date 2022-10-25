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
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Alert.setAlert;

public class ModifyCustomer implements Initializable {
    @FXML private ComboBox<Country> modCustCountry;
    @FXML private ComboBox<Division> modCustDiv;
    @FXML private TextField modCustId;
    @FXML private TextField modCustName;
    @FXML private TextField modCustNum;
    @FXML private TextField modCustAddress;
    @FXML private TextField modCustPost;

    public void onActionCountry(ActionEvent actionEvent) throws SQLException {
        Country selectedCountry = modCustCountry.getValue();
        modCustDiv.setItems(DivisionDAO.getCountryDiv(selectedCountry.getCountryId()));
        modCustDiv.setDisable(false);
    }

    private boolean validateInputs(){
        int custId = Integer.parseInt(modCustId.getText());
        String custName = modCustName.getText();
        String custAddress = modCustAddress.getText();
        String custPost = modCustPost.getText();
        String custNum = modCustNum.getText();

        if(custName.isEmpty() || custName.isBlank()){
            setAlert("Error", "Customer must have a name");
        }else if(custAddress.isEmpty() || custAddress.isBlank()){
            setAlert("Error", "Customer must have an address");
        }else if(custPost.isEmpty() || custPost.isBlank()){
            setAlert("Error", "Customer must have a postal code");
        }else if(custNum.isEmpty() || custNum.isBlank()){
            setAlert("Error", "Customer must have a phone number");
        }else {
            Division custDiv = modCustDiv.getValue();
            int custDivId = custDiv.getDivId();

            CustomerDAO.modCustomer(custId, custName, custAddress, custPost, custNum, custDivId);
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

    public void sendCust(Customer selectedCust) throws SQLException {
        modCustId.setText(String.valueOf(selectedCust.getCustId()));
        modCustName.setText(selectedCust.getCustName());
        modCustNum.setText(selectedCust.getCustNum());
        modCustAddress.setText(selectedCust.getCustAddress());
        modCustPost.setText(selectedCust.getCustPost());
        modCustCountry.setValue(CountryDAO.getCountry(selectedCust.getCustDivId()));
        modCustDiv.setValue(DivisionDAO.getDiv(selectedCust.getCustDivId()));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modCustId.setDisable(true);

        try{
            modCustCountry.setItems(CountryDAO.getAllCountries());
//            modCustCountry.getSelectionModel().selectFirst();
//
//            modCustDiv.setItems(DivisionDAO.getCountryDiv(modCustCountry.getValue().getCountryId()));
//            modCustDiv.getSelectionModel().selectFirst();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
