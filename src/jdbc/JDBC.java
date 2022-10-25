package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "root"; //"sqlUser";
    private static final String password = "root"; //"Passw0rd!";
    public static Connection connection;

    public static void openConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection Successful!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection Closed!");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }



}
