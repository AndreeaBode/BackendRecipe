package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.LoveReact;

import java.util.List;
import java.util.Optional;

public interface LoveReactRepository extends JpaRepository<LoveReact, Integer> {
    void deleteByUserIdAndRecipeId(int userId, int recipeId);
    Optional<LoveReact> findByUserIdAndRecipeIdAndName(int userId, int recipeId, String name);
    List<LoveReact> findByUserIdAndRecipeId(int userId, int recipeId);
    List<LoveReact> findAll();
    List<LoveReact> findByUserId(int userId);
}
