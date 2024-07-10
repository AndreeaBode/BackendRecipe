package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.ExtractedRecipe;
import sd.entities.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findAllByRecipe(ExtractedRecipe recipe);

}
