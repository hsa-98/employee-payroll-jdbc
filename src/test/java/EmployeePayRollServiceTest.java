import employeepayroll.EmployeePayrollData;
import employeepayroll.EmployeePayrollService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayRollServiceTest {
    @Test
    void givenEmployeePayroll_Should_Match_Count() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData =   employeePayrollService.readData();
        Assertions.assertEquals(3,employeePayrollData.size());
    }
}