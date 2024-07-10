package sd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sd.entities.ExtractedRecipe;
import sd.entities.Step;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Integer> {
    List<Step> findAllByRecipe(ExtractedRecipe recipe);
}
