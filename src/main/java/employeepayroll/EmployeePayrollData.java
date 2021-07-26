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

    @Override
    public String toString() {
        return "EmployeePayrollData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", start=" + start +
                '}' ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id &&
                Double.compare(that.salary, salary) == 0 &&
                name.equals(that.name);
    }
}
