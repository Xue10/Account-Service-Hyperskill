package account.repository;

import account.business.data.Salary;
import account.business.data.SalaryId;
import org.springframework.data.repository.CrudRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends CrudRepository<Salary, SalaryId> {

    Optional<Salary> findByEmployeeAndPeriod(String employee, YearMonth period);
    Optional<List<Salary>> findByEmployeeOrderByPeriodDesc(String employee);
}
