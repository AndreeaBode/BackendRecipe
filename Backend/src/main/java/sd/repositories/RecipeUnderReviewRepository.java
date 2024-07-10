package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.RecipeUnderReview;

public interface RecipeUnderReviewRepository extends JpaRepository<RecipeUnderReview, Integer> {
}
