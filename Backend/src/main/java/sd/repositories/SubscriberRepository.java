package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sd.entities.Subscriber;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    Optional<Subscriber> findByEmail(String email);
}

