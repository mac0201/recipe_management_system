package recipes.persistence_layer;

import org.springframework.data.repository.CrudRepository;
import recipes.business_layer.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
