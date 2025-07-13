package training.project.price_comparison.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.project.price_comparison.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
