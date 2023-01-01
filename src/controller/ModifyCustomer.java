package controller;

import dao.CountryDAO;
import dao.CustomerDAO;
import dao.DivisionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class creates the Modify Customer controller which extends from the Add Customer controller.
 */
public class ModifyCustomer extends AddCustomer implements Initializable {
    /** This method populates the division ComboBox based on country selection.
     * @param actionEvent the user selects a Country */
    //Populates division selection based on country
    @Override
    public void onActionCountry(ActionEvent actionEvent) throws SQLException {
        division.setItems(DivisionDAO.getCountryDiv(country.getValue().getCountryId()));
        division.getSelectionModel().clearSelection();
        division.getSelectionModel().selectFirst();
        division.setDisable(false);
    }

    /** This method saves and updates the modifications to the customer. */
    //Saves modifications
    @Override
    public void saveCustomer(Customer customer) {
        CustomerDAO.modCustomer(
                Integer.parseInt(id.getText()),
                customer.getCustName(),
                customer.getCustAddress(),
                customer.getCustPost(),
                customer.getCustPhone(),
                customer.getCustDivId());
    }

    /**
     * This method populates the Customer table with the selected customer from the Main Menu.
     *
     * @param selectedCust the selected customer from the Main Menu
     */
    //Populates selection from Customer table
    public void sendCust(Customer selectedCust) throws SQLException {
        id.setText(String.valueOf(selectedCust.getCustId()));
        name.setText(selectedCust.getCustName());
        phone.setText(selectedCust.getCustPhone());
        address.setText(selectedCust.getCustAddress());
        postal.setText(selectedCust.getCustPost());
        country.setValue(CountryDAO.getCountry(selectedCust.getCustDivId()));
        division.setValue(DivisionDAO.getDiv(selectedCust.getCustDivId()));
    }

    /** This method initializes the Modify Customer screen. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        division.setDisable(true);

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
