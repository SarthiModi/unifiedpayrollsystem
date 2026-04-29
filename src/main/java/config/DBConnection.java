package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL ="jdbc:mysql://127.0.0.1:3306/payroll_system";
    private static final String USER = "root";
    private static final String PASSWORD = "Sarthi@33333";
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}