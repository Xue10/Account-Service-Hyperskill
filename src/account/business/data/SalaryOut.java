package account.business.data;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class SalaryOut {

    private String name;
    private String lastname;
    private YearMonth period;
    private long salary;

    public SalaryOut() {
    }

    public SalaryOut(String name, String lastname, YearMonth period, long salary) {
        this.name = name;
        this.lastname = lastname;
        this.period = period;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPeriod() {
        DateTimeFormatter out = DateTimeFormatter.ofPattern("MMMM-uuuu");
        return period.format(out);
    }

    public String getSalary() {
        return salary / 100 + " dollar(s) " + salary % 100 + " cent(s)";
    }

}
