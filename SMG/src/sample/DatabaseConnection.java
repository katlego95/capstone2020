package sample;

import java.sql.Connection;
import java.sql.DriverManager;




public class DatabaseConnection {
    public Connection databaseLink;


    public Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://192.168.88.62:3306/productdb";
            String username = "root";
            String password = "smg123";


            Class.forName(driver);
            databaseLink = DriverManager.getConnection(url, username, password);
            System.out.println("connected");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }

}
