package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class creates the Java Database Connectivity controller.
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser"; //"root";
    private static final String password = "Passw0rd!"; //"root";
    public static Connection connection;

    /**
     * This method opens the initial connection with the database.
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    /** This method retrieves the open connection. */
    public static Connection getConnection(){
        return connection;
    }

    /** This method closes the connection with the database. */
    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection Closed!");
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

}
