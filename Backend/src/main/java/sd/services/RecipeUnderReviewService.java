package sd.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sd.entities.RecipeUnderReview;
import sd.repositories.RecipeUnderReviewRepository;

import java.util.List;

@Service
public class RecipeUnderReviewService {

    private final RecipeUnderReviewRepository recipeUnderReviewRepository;

    @Autowired
    public RecipeUnderReviewService(RecipeUnderReviewRepository recipeUnderReviewRepository) {
        this.recipeUnderReviewRepository = recipeUnderReviewRepository;
    }

    @Transactional
    public List<RecipeUnderReview> getRecipesUnderReview() {
        return recipeUnderReviewRepository.findAll();
    }

    public void deleteRecipeUnderReview(int recipeId) {
        RecipeUnderReview recipe = recipeUnderReviewRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        recipeUnderReviewRepository.delete(recipe);
    }
}
