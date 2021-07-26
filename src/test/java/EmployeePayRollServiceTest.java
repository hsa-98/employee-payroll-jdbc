import employeepayroll.EmployeePayrollData;
import employeepayroll.EmployeePayrollService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import  Exception.EmployeePayrollException;

import java.time.LocalDate;
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

    @Test
    void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws EmployeePayrollException {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readData(EmployeePayrollService.IOService.DB_IO);
        LocalDate startDate =LocalDate.of(2020,9,30);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollDataList =
                employeePayrollService.readEmployeePayrollForDateRange(EmployeePayrollService.IOService.DB_IO,startDate,endDate);
        Assertions.assertEquals(1,employeePayrollDataList.size());
    }
}