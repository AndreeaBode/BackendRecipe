package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
