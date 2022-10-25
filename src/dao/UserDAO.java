package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static boolean validateUsername(String username) throws SQLException {
        String sqlStatement = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    public static boolean validateLoginCredentials(String username, String password) throws SQLException {

        String sqlStatement = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

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

    public static User getUser(int userId) throws SQLException{
        String sqlStatement = "SELECT * FROM users WHERE User_ID = " + userId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {
            return new User(
                    result.getInt("User_ID"),
                    result.getString("User_Name"),
                    result.getString("Password")
            );
        }
        return null;
    }




}
