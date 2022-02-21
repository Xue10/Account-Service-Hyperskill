package account.business.data;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;


public class SalaryId implements Serializable {

    private String employee;
    private YearMonth period;

    public SalaryId() {
    }

    public SalaryId(String employee, YearMonth period) {
        this.employee = employee.toLowerCase();
        this.period = period;
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

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryId salaryId = (SalaryId) o;
        return employee.equals(salaryId.employee.toLowerCase()) && period.equals(salaryId.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee.toLowerCase(), period);
    }
}
