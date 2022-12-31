package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains the User database methods which handle SQL operations on Users.
 */
public class UserDAO {
    /** This method validates usernames.
     * @param username the username being validated
     * @return returns true if the username exists in the database */
    public static boolean validateUsername(String username) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    /** This method validates login credentials.
     * @param username the username being validated
     * @param password the password being validated
     * @return returns true if both the username and the password are a match in the database */

    public static boolean validateLoginCredentials(String username, String password) throws SQLException {

        String sqlStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    /** This method gets all Users from the database.
     * @return returns an observable list of all the users in the database */
    public static ObservableList<User> getAllUsers() throws Exception {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM users";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            allUsers.addAll(
                    new User(
                            result.getInt("User_ID"),
                            result.getString("User_Name"),
                            result.getString("Password")
                    )
            );
        }
        return allUsers;
    }

    /** This method retrieves a User with a matching ID.
     * @param userId the User ID
     * @return returns a User with a matching ID */
    public static User getUser(int userId) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_ID = " + userId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
            return new User(
                    result.getInt("User_ID"),
                    result.getString("User_Name"),
                    result.getString("Password")
            );
        }
        return null;
    }

    /** This method retrieves a User with a matching username.
     * @param userName the username
     * @return returns a User with a matching username */
    public static User getUser(String userName) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_Name = \"" + userName + "\"";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
            return new User(
                    result.getInt("User_ID"),
                    result.getString("User_Name"),
                    result.getString("Password")
            );
        }
        return null;
    }

}
