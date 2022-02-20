package account.repository;

import account.business.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findTopByEmailIgnoreCase(String email);
}
