package account.repository;

import account.business.data.RoleGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleGroupRepository extends CrudRepository<RoleGroup, Long> {
    Optional<RoleGroup> findByName(String name);
    boolean existsByName(String name);
}
