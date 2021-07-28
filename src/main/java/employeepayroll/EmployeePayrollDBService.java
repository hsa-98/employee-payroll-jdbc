package employeepayroll;

import Exception.EmployeePayrollException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        return this.getEmployeeDataUsingDB(sql);

    }

    public List<EmployeePayrollData> getEmployeeForDateRange(LocalDate startDate, LocalDate endDate) throws EmployeePayrollException {
        String sql = String.format("Select * FROM employeedata WHERE Start BETWEEN '%s' AND '%s';",
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getEmployeeDataUsingDB(sql);

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
            e.printStackTrace();
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

    private List<EmployeePayrollData> getEmployeeDataUsingDB(String sql) throws EmployeePayrollException{
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            throw new EmployeePayrollException("Check Read data");
        }
        return employeePayrollDataList;
    }


    private void preparedStatementForEmployeeData() throws EmployeePayrollException {
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
            throw new EmployeePayrollException("check updateEmployeeDataUsingStatement");
        }
    }


    public Map<String, Double> getAvgSalaryByGender() throws SQLException, ClassNotFoundException {
        String sql = "SELECT gender,AVG(SALARY) as avg_Salary from employeedata GROUP BY gender;";
        Map<String,Double>avgSalary = new HashMap<>();
        try(Connection connection=this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String gender = resultSet.getString("gender");
                Double salary = resultSet.getDouble("avg_salary");
                avgSalary.put(gender,salary);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return avgSalary;
    }

    public EmployeePayrollData addEmployeeToDB(String name, String gender, Double salary, LocalDate start) throws SQLException, ClassNotFoundException {
        int employeeId = -1;
        EmployeePayrollData employeePayrollData = null;
        String sql = String.format("INSERT INTO employeedata (name,gender,salary,start)"+"VALUES('%s' , '%s' , '%s',' %s')",name,gender,salary,
                Date.valueOf(start));
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) employeeId = resultSet.getInt(1);
            }
            employeePayrollData = new EmployeePayrollData(employeeId,name,salary,start);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  employeePayrollData;
    }
}
