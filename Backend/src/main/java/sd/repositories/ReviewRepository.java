package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findByIdAndUserId(int extractedId, int userId);
    Optional<Review> findByExtractedRecipe_IdAndUserId(int extractedId, int userId);
    Optional<Review> findByAddedRecipe_IdAndUserId(int addedId, int userId);
}
