package kevs.dev.airbnb;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){

        String databaseName = "airbnb";
        String databaseUser = "root";
        String databasePassword = "1234";
//        String url = STR."jdbc:mysql://localhost/\{databaseName}";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("MySQL Database connected successfully!");
        }catch (Exception error){
            System.out.println(error.getMessage());
        }
        return databaseLink;
    }
}
