package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains the Customer database methods which handle SQL operations on Customers.
 */
public class CustomerDAO {
    /** This method gets all Customers from the database.
     * @return returns an observable list of all the customers in the database. */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM customers";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
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

    /**
     * This method adds a Customer to the database.
     *
     * @param custName    customer name
     * @param custAddress customer address
     * @param custPost    customer postal code
     * @param custNum     customer phone number
     * @param custDivId   customer division ID
     */
    public static void addCustomer(String custName, String custAddress, String custPost, String custNum, int custDivId) {
        try {
            String sqlStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custAddress);
            preparedStatement.setString(3, custPost);
            preparedStatement.setString(4, custNum);
            preparedStatement.setInt(5, custDivId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method modifies a Customer in the database.
     *
     * @param custId      customer ID
     * @param custName    customer name
     * @param custAddress customer address
     * @param custPost    customer postal code
     * @param custNum     customer phone number
     * @param custDivId   customer division ID
     */
    public static void modCustomer(int custId, String custName, String custAddress, String custPost, String custNum, int custDivId) {
        try {
            String sqlStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = " + custId;
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

            preparedStatement.setString(1, custName);
            preparedStatement.setString(2, custAddress);
            preparedStatement.setString(3, custPost);
            preparedStatement.setString(4, custNum);
            preparedStatement.setInt(5, custDivId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes a Customer from the database using a Customer ID.
     *
     * @param custId the customer ID
     */
    public static void deleteCustomer(int custId) throws SQLException {
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        preparedStatement.setInt(1, custId);
        preparedStatement.execute();
    }

    /**
     * This method gets a Customer from the database using a Customer ID.
     *
     * @param custId the customer ID
     */
    public static Customer getCustomer(int custId) throws SQLException {
        String sqlStatement = "SELECT * FROM customers WHERE Customer_ID = " + custId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
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

    /**
     * This method deletes a Customer's associated appointments using a Customer ID.
     *
     * @param custId the customer ID
     */
    public static void deleteAssociatedApps(int custId) throws SQLException {
        String sqlStatement = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

        preparedStatement.setInt(1, custId);
        preparedStatement.executeUpdate();
    }

}
