package employeepayroll;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmployeePayrollService {
    /**
     *  the function establishes a connection with sql server and returns the connection to the caler
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private Connection getConection() throws SQLException, ClassNotFoundException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payrollservicedb?useSSL=false";
        String userName = "root";
        String password = "Chocoslam!123";
        Connection con;

        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded");
        System.out.println("Connecting to database " + jdbcURL);
        con = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful!!!" + con);
        return con;
    }

    /**
     * This method reads data from sql server and returns the data as a list.
     * @return
     */
    public List<EmployeePayrollData> readData(){
        String sql = "SELECT * FROM employeedata";
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            Connection connection = this.getConection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id,name,salary,start));
            }
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }
}
