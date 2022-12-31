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

import static controller.Login.setAlert;

/**
 * This class creates the Add Customer screen controller.
 */
public class AddCustomer implements Initializable {
    @FXML
    protected ComboBox<Country> country;
    @FXML
    protected ComboBox<Division> division;
    @FXML
    protected TextField id;
    @FXML
    protected TextField name;
    @FXML
    protected TextField phone;
    @FXML
    protected TextField address;
    @FXML
    protected TextField postal;

    /**
     * This method populates the division ComboBox based on country selection.
     *
     * @param actionEvent the user selects a Country
     */
    //Populates division selection based on country
    public void onActionCountry(ActionEvent actionEvent) throws SQLException {
        division.setItems(DivisionDAO.getCountryDiv(country.getValue().getCountryId()));
        division.getSelectionModel().selectFirst();
    }

    /**
     * This method validates user inputs.
     *
     * @return returns true if inputs are valid
     */
    //Input validation
    public boolean validateInputs() {
        String custName = name.getText();
        String custAddress = address.getText();
        String custPostal = postal.getText();
        String custPhone = phone.getText();

        if (custName.isEmpty() || custName.isBlank()){
            setAlert("Error", "Customer must have a name");
        } else if (custAddress.isEmpty() || custAddress.isBlank()){
            setAlert("Error", "Customer must have an address");
        } else if (custPostal.isEmpty() || custPostal.isBlank()){
            setAlert("Error", "Customer must have a postal code");
        } else if (custPhone.isEmpty() || custPhone.isBlank()){
            setAlert("Error", "Customer must have a phone number");
        } else {
            Division custDiv = division.getValue();
            int custDivId = custDiv.getDivId();

            saveCustomer(new Customer(custName, custAddress, custPostal, custPhone, custDivId));
            return true;
        }
        return false;
    }

    /**
     * This method saves a new customer.
     *
     * @param customer the customer
     */
    //Saves a new customer
    public void saveCustomer(Customer customer) {
        CustomerDAO.addCustomer(
                customer.getCustName(),
                customer.getCustAddress(),
                customer.getCustPost(),
                customer.getCustPhone(),
                customer.getCustDivId());
    }

    /**
     * This method executes the save customer action and navigate back to the Main Menu.
     *
     * @param actionEvent the user clicks on the Save button
     */
    //Saves customer; navigates back to MainMenu
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method cancels the customer creation and navigates back to the Main Menu.
     *
     * @param actionEvent the user clicks on the Cancel button
     */
    //Cancels customer creation; navigates back to MainMenu
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to cancel without saving?");

        if (result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Scheduling Application");
            stage.centerOnScreen();
            stage.show();
        }
    }

    /** This method initializes the Add Customer screen. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setDisable(true);

        //Initializes selections
        try {
            country.setItems(CountryDAO.getAllCountries());
            country.getSelectionModel().selectFirst();

            division.setItems(DivisionDAO.getCountryDiv(country.getValue().getCountryId()));
            division.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}