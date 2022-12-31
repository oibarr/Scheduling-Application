package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains the Division database methods which handle SQL operations on Divisions.
 */
public class DivisionDAO {
    /** This method retrieves a Division with a matching ID.
     * @param divisionId the Division ID
     * @return returns a Division with a matching ID. */
    public static Division getDiv(int divisionId) throws SQLException {
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
            return new Division(
                    result.getInt("Division_ID"),
                    result.getString("Division"),
                    result.getInt("Country_ID")
            );
        }
        return null;
    }

    /** This method returns a list of Divisions with a matching Country ID.
     * @param countryID the Country ID
     * @return returns an observable list of divisions with a matching Country ID. */
    public static ObservableList<Division> getCountryDiv(int countryID) throws SQLException {
        ObservableList<Division> countryDiv = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Country_ID = " + countryID;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            countryDiv.add(
                    new Division(
                            result.getInt("Division_ID"),
                            result.getString("Division"),
                            result.getInt("Country_ID")
                    )
            );
        }
        return countryDiv;
    }
}
