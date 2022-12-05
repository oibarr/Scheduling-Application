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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class MainMenu implements Initializable {
    private final ObservableList<Appointment> Appointments = FXCollections.observableArrayList();
    private final ObservableList<Customer> Customers = FXCollections.observableArrayList();

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

    //Radio Buttons
    public void onActionAll(ActionEvent actionEvent) throws Exception {
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.getAllAppointments());
    }

    public void onActionWeek(ActionEvent actionEvent) throws SQLException {
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.filterByWeek());
    }

    public void onActionMonth(ActionEvent actionEvent) throws Exception {
        Appointments.clear();
        Appointments.addAll(AppointmentDAO.filterByMonth());
    }

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

    //Deletes appointment
    public void onActionDeleteApp(ActionEvent actionEvent) {
        if(appTable.getSelectionModel().getSelectedItem() != null) {


            Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to delete selected appointment(s)?");

            if (result.isPresent() && result.get() == ButtonType.OK) {
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

    //Navigates to AddCustomer screen
    public void onActionAddCust(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

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

    //Checks for associated appointments
    public static boolean checkAssociatedApps(int custId) throws Exception {
        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> associatedAppointments = FXCollections.observableArrayList();

        //Lambda #2
        appointments.forEach(a -> {
            if (custId == a.getAppCustId()){
                associatedAppointments.add(a);
            }
        });

        return associatedAppointments.size() != 0;

    }

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
                }else{
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
        }else{
            setAlert("Error", "No customer(s) selected");
        }
    }

    //Reports Dashboard
    public void onActionReportByType(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/TypeScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Type");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
    public void onActionReportByMonth(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MonthScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Month");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
    public void onActionReportByContact(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Contact");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }
    public void onActionReportByLocation(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LocationScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments by Location");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void onActionAddApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onActionExit(ActionEvent actionEvent) {

        Optional<ButtonType> result = setAlert("Confirmation", "Are you sure you'd like to Exit?");

        if(result.isPresent() && result.get() == ButtonType.OK){
            JDBC.closeConnection();
            System.exit(0);
        }
    }

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
}
