package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
    public static ObservableList<Customer> getAllCustomers() throws SQLException{
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM customers";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()){
            allCustomers.add(
                    new Customer(
                            result.getInt("Customer_ID"),
                            result.getString("Customer_Name"),
                            result.getString("Address"),
                            result.getString("Postal_Code"),
                            result.getString("Phone"),
                            result.getInt("Division_ID")
                    )
            );
        }
        return allCustomers;
    }

    public static void addCustomer(String custName, String custAddress, String custPost, String custNum, int custDivId){
        try{
            String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custAddress);
            preparedStatement.setString(3, custPost);
            preparedStatement.setString(4, custNum);
            preparedStatement.setInt(5, custDivId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void modCustomer(int custId, String custName, String custAddress, String custPost, String custNum, int custDivId){
        try{
            String sqlStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = " + custId;
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custAddress);
            preparedStatement.setString(3, custPost);
            preparedStatement.setString(4, custNum);
            preparedStatement.setInt(5, custDivId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(int custId) throws SQLException {
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setInt(1, custId);
        preparedStatement.execute();
    }


    public static Customer getCustomer(int custId) throws SQLException{
        String sqlStatement = "SELECT * FROM customers WHERE Customer_ID = " + custId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {
            return new Customer(
                    result.getInt("Customer_ID"),
                    result.getString("Customer_Name"),
                    result.getString("Address"),
                    result.getString("Postal_Code"),
                    result.getString("Phone"),
                    result.getInt("Division_ID")
            );
        }
        return null;
    }

    public static boolean checkAssociatedApps(int custId) throws Exception {
        ObservableList<Appointment> appointments = AppointmentDAO.getAllAppointments();

        for(Appointment a : appointments){
            if(custId == a.getAppCustId() && a.getAppId() != 0){
                return true;
            }
        }
        return false;
    }

    public static void deleteAssociatedApps(int custId) throws SQLException {
        String sqlStatement = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

        preparedStatement.setInt(1, custId);
        preparedStatement.executeUpdate();
    }





}
