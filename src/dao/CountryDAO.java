package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM countries";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            allCountries.add(
                    new Country(
                            result.getInt("Country_ID"),
                            result.getString("Country")
                    )
            );
        }
        return allCountries;
    }

    public static Country getCountry(int divisionId) throws SQLException {
        String sqlStatement = "SELECT countries.Country_ID, Country FROM countries INNER JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID WHERE Division_ID = " + divisionId;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
            return new Country(
                    result.getInt("Country_ID"),
                    result.getString("Country")
            );
        }
        return null;
    }

}
