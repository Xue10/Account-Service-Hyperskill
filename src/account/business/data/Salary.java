package account.business.data;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity
@Table
@IdClass(SalaryId.class)
public class Salary {
    @Id
    private String employee;
    @Id
    private YearMonth period;
//    @Min(0)
    private long salary;

    public Salary() {
    }

    public Salary(String employee, String period, long salary) {
        this.employee = employee.toLowerCase();
//        checkPeriodFormat(period);
        this.period = convertPeriod(period);
        this.salary = salary;
    }

    public Salary(String employee, YearMonth period, long salary) {
        this.employee = employee.toLowerCase();
        this.period = period;
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
        checkPeriodFormat(period);
        this.period = convertPeriod(period);
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public static void checkPeriodFormat(String period) {
        DateTimeFormatter in = DateTimeFormatter.ofPattern("MM-uuuu");
        try {
            YearMonth.parse(period, in);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static YearMonth convertPeriod(String period) {
        DateTimeFormatter in = DateTimeFormatter.ofPattern("MM-uuuu");
        return YearMonth.parse(period, in);
    }
}
