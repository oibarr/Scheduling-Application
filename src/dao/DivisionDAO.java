package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdbc.JDBC;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {
    public static Division getDiv(int divisionId) throws SQLException{
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

    public static ObservableList<Division> getCountryDiv(int countryID) throws SQLException{
        ObservableList<Division> countryDiv = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * FROM first_level_divisions WHERE Country_ID = " + countryID;
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sqlStatement);
        ResultSet result = preparedStatement.executeQuery();

        while(result.next()){
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
