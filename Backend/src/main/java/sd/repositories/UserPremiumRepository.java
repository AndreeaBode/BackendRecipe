package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.User;

public interface UserPremiumRepository extends JpaRepository<User, Integer> {
}