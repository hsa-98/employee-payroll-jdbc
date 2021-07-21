package employeepayroll;

import java.time.LocalDate;

/**
 * @author Harsh
 * This class stores employee data
 */
public class EmployeePayrollData {
    int id;
    String name;
    Double salary;
    LocalDate start;

    public EmployeePayrollData(int id, String name, Double salary, LocalDate start) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.start = start;
    }
}
