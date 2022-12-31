package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains the Contact database methods which handle SQL operations on Contacts.
 */
public class ContactDAO {
    /** This method gets all Contacts from the database.
     * @return returns an observable list of all the contacts in the database */
    public static ObservableList<Contact> getAllContacts() throws Exception {

        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM Contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            allContacts.add(
                    new Contact(
                            result.getInt("Contact_ID"),
                            result.getString("Contact_Name"),
                            result.getString("Email")
                    )
            );
        }
        return allContacts;
    }

    /** This method gets a Contact using the contact ID.
     * @param contId the contact ID
     * @return returns a contact with a matching contact ID */
    public static Contact getContact(int contId) throws SQLException {
        String sqlStatement = "SELECT * FROM contacts WHERE Contact_ID = " + contId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
            return new Contact(
                    result.getInt("Contact_ID"),
                    result.getString("Contact_Name"),
                    result.getString("Email")
            );
        }
        return null;
    }
}
