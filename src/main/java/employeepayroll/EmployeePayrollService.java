package employeepayroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import  Exception.EmployeePayrollException;

public class EmployeePayrollService {
    private EmployeePayrollDBService employeePayrollDBService;
    private List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();


    public EmployeePayrollService()   {
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public List<EmployeePayrollData> readEmployeePayrollForDateRange(IOService ioService,
                                                                     LocalDate startDate, LocalDate endDate)
                                                                        throws EmployeePayrollException {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getEmployeeForDateRange(startDate,endDate);
        return null;
    }


    public enum IOService {
        DB_IO;
    }

    public List<EmployeePayrollData> readData(IOService ioService) throws EmployeePayrollException {
        if (ioService.equals(IOService.DB_IO))
            employeePayrollDataList = employeePayrollDBService.readData();
        return employeePayrollDataList;
    }


    public void updateEmployeeSalary(String name, double salary) throws EmployeePayrollException {
        int result = employeePayrollDBService.updateEmployeeData(name, salary);
        if (result == 0)
            return;
        EmployeePayrollData employeePayrollData = getEmployeePayrollData(name);
        if (employeePayrollData == null) return;
        employeePayrollData.salary = salary;
    }


    private EmployeePayrollData getEmployeePayrollData(String name) {
        EmployeePayrollData employeePayrollData = employeePayrollDataList.stream()
                .filter(x -> x.name.equals(name))
                .findFirst()
                .orElse(null);
        return employeePayrollData;
    }


    public Boolean checkEmployeePayRollInSync(String name) throws EmployeePayrollException {
        List<EmployeePayrollData> employeePayrollData = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollData.get(0).equals(getEmployeePayrollData(name));
    }



}
