package account.business;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity
@IdClass(SalaryId.class)
public class Salary {
    @Id
    private String employee;
    @Id
    private YearMonth period;
    @Min(0)
    private long salary;

    public Salary() {
    }

    public Salary(String employee, String period, int salary) {
        DateTimeFormatter in = DateTimeFormatter.ofPattern("MM-uuuu");
        this.employee = employee.toLowerCase();
        this.period = YearMonth.parse(period, in);
        this.salary = salary;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee.toLowerCase();
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        DateTimeFormatter in = DateTimeFormatter.ofPattern("MM-uuuu");
        this.period = YearMonth.parse(period, in);
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }
}
