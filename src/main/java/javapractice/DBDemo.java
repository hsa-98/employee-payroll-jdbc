package javapractice;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DBDemo {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/payrollservice?useSSL=false";
        String userName = "root";
        String password = "Chocoslam!123";
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver.", e);
        }
        listDrivers();
        try {
            System.out.println("Connecting to database " + jdbcURL);
            con = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection is successful!!!" +con);
        }
        catch (Exception e){
            e.printStackTrace();

    }

}

    public static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driver =  driverList.nextElement();
            System.out.println("  " + driver.getClass().getName());
        }
    }
}
