package sd.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sd.dtos.ExtractedDTO;
import sd.entities.ExtractedRecipe;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractedRecipeRepository extends JpaRepository<ExtractedRecipe, Integer> {
    boolean existsByTitle(String title);

    List<ExtractedRecipe> findByDishTypesContaining(String dishType);
    List<ExtractedRecipe> findByDietsContaining(String diet);
    List<ExtractedRecipe> findAll();
    @EntityGraph(attributePaths = {"ingredients", "steps"})
    Optional<ExtractedRecipe> findById(Integer id);
}
