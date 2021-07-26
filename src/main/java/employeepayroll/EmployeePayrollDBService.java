package employeepayroll;
import  Exception.EmployeePayrollException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class EmployeePayrollDBService {

    private PreparedStatement employeePayrollDataStatement;
    private static EmployeePayrollDBService employeePayrollDBService;

    /**
     * The constructor is private because we want the class to be singleton.
     */
    private EmployeePayrollDBService() {

    }

    public static EmployeePayrollDBService getInstance() {
        if (employeePayrollDBService == null)
            employeePayrollDBService = new EmployeePayrollDBService();
        return employeePayrollDBService;
    }

    /**
     * the function establishes a connection with sql server and returns the connection to the caller
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private Connection getConnection() throws SQLException, ClassNotFoundException {
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
     *
     * @return
     */
    public List<EmployeePayrollData> readData() throws EmployeePayrollException {
        String sql = "SELECT * FROM employeedata";
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection();) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                LocalDate start = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, salary, start));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new EmployeePayrollException("Check Read data");
        }
        return employeePayrollDataList;
    }

    public List<EmployeePayrollData> getEmployeePayrollData(String name) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollDataList = null;
        if (this.employeePayrollDataStatement == null)
            this.preparedStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);


        } catch (SQLException e) {

        }
        return employeePayrollDataList;
    }


    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollData = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollData.add(new EmployeePayrollData(id, name, salary, startDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollData;
    }


    private void preparedStatementForEmployeeData() throws  EmployeePayrollException {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employeedata WHERE name =?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new EmployeePayrollException("check preparedStatementForEmployeeData");
        }
    }


    public int updateEmployeeData(String name, double salary) throws EmployeePayrollException {
        return updateEmployeeDataUsingStatement(name, salary);
    }


    private int updateEmployeeDataUsingStatement(String name, double salary) throws EmployeePayrollException {
        String sql = String.format("UPDATE employeedata  SET  salary = %.2f where name ='%s'; ", salary, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new EmployeePayrollException("check updateEmployeeDataUsingStatement") ;
        }
    }
}
