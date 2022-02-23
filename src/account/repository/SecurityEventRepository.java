package account.repository;

import account.business.data.SecurityEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SecurityEventRepository extends CrudRepository<SecurityEvent, Long> {
}
