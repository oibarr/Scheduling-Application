package controller;

import dao.AppointmentDAO;
import dao.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdbc.JDBC;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.Login.setAlert;

/**
 * This class creates the Main Menu controller.
 */
public class MainMenu implements Initializable {
    private final ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
    private final ObservableList<Customer> Customers = FXCollections.observableArrayList();
    ObservableList<Appointment> appSearchResults = FXCollections.observableArrayList();
    ObservableList<Customer> custSearchResults = FXCollections.observableArrayList();

    @FXML
    private TableView<Appointment> appTable;
    @FXML
    private TableColumn<Appointment, Integer> Appointment_ID;
    @FXML
    private TableColumn<Appointment, String> Title;
    @FXML
    private TableColumn<Appointment, String> Description;
    @FXML
    private TableColumn<Appointment, String> Location;
    @FXML
    private TableColumn<Appointment, String> Type;
    @FXML
    private TableColumn<Appointment, Calendar> Start;
    @FXML
    private TableColumn<Appointment, Calendar> End;
    @FXML
    private TableColumn<Appointment, Integer> App_Customer_ID;
    @FXML
    private TableColumn<Appointment, Integer> User_ID;
    @FXML
    private TableColumn<Appointment, Integer> Contact_ID;
    @FXML
    TextField appSearchText;

    @FXML
    private TableView<Customer> custTable;
    @FXML
    private TableColumn<Customer, Integer> Customer_ID;
    @FXML
    private TableColumn<Customer, String> Customer_Name;
    @FXML
    private TableColumn<Customer, String> Address;
    @FXML
    private TableColumn<Customer, String> Postal_Code;
    @FXML
    private TableColumn<Customer, String> Phone;
    @FXML
    private TableColumn<Customer, Integer> Division_ID;
    @FXML
    TextField custSearchText;

    /**
     * This method clears and updates the appointments table with all appointments when the "All" radio button is selected.
     *
     * @param actionEvent the user selects the "All" radio button
     */
    //Radio Buttons
    public void onActionAll(ActionEvent actionEvent) throws Exception {
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.getAllAppointments());
    }

    /**
     * This method clears and updates the appointments table with appointments scheduled for the current week when the "Week" radio button is selected.
     *
     * @param actionEvent the user selects the "Week" radio button
     */
    public void onActionWeek(ActionEvent actionEvent) throws SQLException {
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.filterByWeek());
    }

    /**
     * This method clears and updates the appointments table with appointments scheduled for the current month when the "Month" radio button is selected.
     *
     * @param actionEvent the "Month" radio button is selected
     */
    public void onActionMonth(ActionEvent actionEvent) throws Exception {
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.filterByMonth());
    }

    /**
     * This method navigates to the Modify Appointment screen and obtains a selection from the Appointments table.
     *
     * @param actionEvent the user clocks on the Modify button
     */
    //Navigates to ModifyAppointment screen; obtains selection from Appointments table
    public void onActionModifyApp(ActionEvent actionEvent) throws IOException, SQLException {
        if (appTable.getSelectionModel().getSelectedItem() != null){

            FXMLLoader loader = new FXMLLoader(getClass().getResource(("/view/ModifyAppointment.fxml")));
            Parent root = loader.load();

            ModifyAppointment modifyAppointmentControllerReference = loader.getController();
            modifyAppointmentControllerReference.sendApp(appTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Appointment");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();


        } else {
            setAlert("Error", "No appointment(s) selected");
        }
    }

    /**
     * This method deletes an appointment.
     *
     * @param actionEvent the user clicks on the Delete button
     */
    //Deletes appointment
    public void onActionDeleteApp(ActionEvent actionEvent) {
        if (appTable.getSelectionModel().getSelectedItem() != null){


            Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to delete selected appointment(s)?");

            if (result.isPresent() && result.get() == ButtonType.OK){
                int appId = appTable.getSelectionModel().getSelectedItem().getAppId();
                String appType = appTable.getSelectionModel().getSelectedItem().getAppType();

                AppointmentDAO.deleteAppointment(appId);

                appTable.getItems().clear();
                try {
                    Appointments.addAll(AppointmentDAO.getAllAppointments());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                appTable.setItems(Appointments);

                setAlert("Information", "Appointment ID: " + appId + " of Type: " + appType + " successfully deleted");

            }

        } else {
            setAlert("Error", "No appointment(s) selected");
        }
    }

    /**
     * This method navigates to the Add Customer screen.
     *
     * @param actionEvent the user clicks on the Add button
     */
    //Navigates to AddCustomer screen
    public void onActionAddCust(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method navigates to the Modify Customer screen and obtains a selected customer.
     *
     * @param actionEvent the user clicks on the Modify button
     */
    //Navigates to ModifyCustomer screen; obtains selected customer
    public void onActionModifyCust(ActionEvent actionEvent) throws IOException, SQLException {
        if (custTable.getSelectionModel().getSelectedItem() != null){

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ModifyCustomer.fxml")));
            Parent root = loader.load();

            ModifyCustomer modifyCustomerControllerReference = loader.getController();
            modifyCustomerControllerReference.sendCust(custTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Customer");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();

        } else {
            setAlert("Error", "No customer(s) selected");
        }

    }

    /** This method checks for associated appointments using a customer ID.
     * Lambda #2: filters appointments that match the customer ID. The use of a lambda simplifies the code and aids readability.
     * @param custId the customer ID
     * @return returns true if there is an associated appointment with the customer ID */
    //Checks for associated appointments
    public static boolean checkAssociatedApps(int custId) throws Exception {

        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();

        //Lambda #2
        ObservableList<Appointment> associatedAppointments = appointments.filtered(a -> a.getAppCustId() == custId);
        return associatedAppointments.size() != 0;

    }

    /** This method deletes a Customer.
     * It checks for customer appointment dependencies before deleting a Customer and allows the user to delete associated appointments as well as confirm customer deletion
     * @param actionEvent the user clicks on the Delete button */
    //Checks for customer appointment dependencies before deleting a Customer; allows the user to delete associated appointments and confirm deletion of the customer
    public void onActionDeleteCust(ActionEvent actionEvent) throws Exception {
        if (custTable.getSelectionModel().getSelectedItem() != null){

            Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to delete selected customer(s)?");

            if (result.isPresent() && result.get() == ButtonType.OK){
                int custId = custTable.getSelectionModel().getSelectedItem().getCustId();

                if (checkAssociatedApps(custId)){

                    setAlert("Warning", "Customer has existing associated appointments");


                    result = setAlert("Confirmation", "Would you like to delete associated appointments\nand proceed with customer deletion?");


                    if (result.isPresent() && result.get() == ButtonType.OK){
                        CustomerDAO.deleteAssociatedApps(custId);
                        CustomerDAO.deleteCustomer(custId);
                        custTable.getItems().clear();
                        appTable.getItems().clear();
                        try {
                            Customers.addAll(CustomerDAO.getAllCustomers());
                            Appointments.addAll(AppointmentDAO.getAllAppointments());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        custTable.setItems(Customers);

                        setAlert("Information", "Customer record successfully deleted");

                    }
                } else {
                    CustomerDAO.deleteCustomer(custId);
                    custTable.getItems().clear();
                    try {
                        Customers.addAll(CustomerDAO.getAllCustomers());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    custTable.setItems(Customers);
                }
            }
        } else {
            setAlert("Error", "No customer(s) selected");
        }
    }

    /**
     * This method navigates to the Type report screen.
     *
     * @param actionEvent the user clicks on the Type button
     */
    //Reports Dashboard
    public void onActionReportByType(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/TypeScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Type");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method navigates to the Month report screen.
     *
     * @param actionEvent the user clicks on the Month button
     */
    public void onActionReportByMonth(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MonthScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Month");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method navigates to the Contact report screen.
     *
     * @param actionEvent the user clicks on the Contact button
     */
    public void onActionReportByContact(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Contact");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method navigates to the Location report screen.
     *
     * @param actionEvent the user clicks on the Location button
     */
    public void onActionReportByLocation(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LocationScreen.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Location");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method navigates to the Add Appointment screen.
     *
     * @param actionEvent the user clicks on the Add button
     */
    public void onActionAddApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * This method exits the application.
     *
     * @param actionEvent the user clicks on the Exit button
     */
    @FXML
    void onActionExit(ActionEvent actionEvent) {

        Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to Exit?");

        if(result.isPresent() && result.get() == ButtonType.OK){
            JDBC.closeConnection();
            System.exit(0);
        }
    }

    /** This method initializes the Main Menu. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //populates Appointments table
        Appointment_ID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        Location.setCellValueFactory(new PropertyValueFactory<>("appLoc"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        App_Customer_ID.setCellValueFactory(new PropertyValueFactory<>("appCustId"));
        User_ID.setCellValueFactory(new PropertyValueFactory<>("appUserId"));
        Contact_ID.setCellValueFactory(new PropertyValueFactory<>("appContId"));

        try {
            Appointments.addAll(AppointmentDAO.getAllAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        appTable.setItems(Appointments);

        //populates Customer table
        Customer_ID.setCellValueFactory(new PropertyValueFactory<>("custId"));
        Customer_Name.setCellValueFactory(new PropertyValueFactory<>("custName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        Postal_Code.setCellValueFactory(new PropertyValueFactory<>("custPost"));
        Phone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        Division_ID.setCellValueFactory(new PropertyValueFactory<>("custDivId"));

        try {
            Customers.addAll(CustomerDAO.getAllCustomers());
        } catch (Exception e) {
            e.printStackTrace();
        }
        custTable.setItems(Customers);

    }

    public void onAppSearch(ActionEvent actionEvent) {
        appSearchResults.clear();
        searchAppointments(appSearchText.getText());
    }

    private void searchAppointments(String appTitle) {
        if (appSearchText.getText().isBlank() || appSearchText.getText().isEmpty()){
            appTable.setItems(Appointments);
        } else {
            for (Appointment a : Appointments) {
                if (a.getAppTitle().toLowerCase().contains(appTitle.toLowerCase())){
                    appSearchResults.add(a);
                }
            }
            appTable.setItems(appSearchResults);
        }
    }

    public void onCustSearch(ActionEvent actionEvent) {
        custSearchResults.clear();
        searchCustomers(custSearchText.getText());
    }

    private void searchCustomers(String custName) {
        if (custSearchText.getText().isBlank() || custSearchText.getText().isEmpty()){
            custTable.setItems(Customers);
        } else {
            for (Customer c : Customers) {
                if (c.getCustName().toLowerCase().contains(custName.toLowerCase())){
                    custSearchResults.add(c);
                }
            }
            custTable.setItems(custSearchResults);
        }
    }
}
