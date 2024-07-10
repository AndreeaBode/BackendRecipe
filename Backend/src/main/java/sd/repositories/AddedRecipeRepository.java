package sd.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.AddedRecipe;
import sd.entities.ExtractedRecipe;

import java.util.List;
import java.util.Optional;

public interface AddedRecipeRepository extends JpaRepository<AddedRecipe, Integer> {
    boolean existsByTitle(String title);
    List<AddedRecipe> findAll();

    @EntityGraph(attributePaths = {"ingredients", "steps"})
    Optional<AddedRecipe> findById(Integer id);

}
