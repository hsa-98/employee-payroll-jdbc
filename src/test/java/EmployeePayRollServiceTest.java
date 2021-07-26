import employeepayroll.EmployeePayrollData;
import employeepayroll.EmployeePayrollService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import  Exception.EmployeePayrollException;

import java.util.List;

public class EmployeePayRollServiceTest {
    @Test
    void givenEmployeePayroll_ShouldMatch_Count() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData =   employeePayrollService.readData(EmployeePayrollService.IOService.DB_IO);
        Assertions.assertEquals(3,employeePayrollData.size());
    }

    @Test
    void givenNewSalary_WhenUpdated_ShouldMatch() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readData(EmployeePayrollService.IOService.DB_IO);
        employeePayrollService.updateEmployeeSalary("Samruddhi",180000);
        Boolean result = employeePayrollService.checkEmployeePayRollInSync("Samruddhi");
        Assertions.assertTrue(result);

    }
}