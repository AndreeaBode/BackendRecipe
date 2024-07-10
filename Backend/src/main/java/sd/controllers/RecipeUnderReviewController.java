package sd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.entities.RecipeUnderReview;
import sd.services.LoveReactService;
import sd.services.RecipeUnderReviewService;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeUnderReviewController {

    private final RecipeUnderReviewService recipeUnderReviewService;

    public RecipeUnderReviewController(RecipeUnderReviewService recipeUnderReviewService) {
        this.recipeUnderReviewService =  recipeUnderReviewService;
    }


    @GetMapping("/under-review")
    public ResponseEntity<List<RecipeUnderReview>> getRecipesUnderReview() {
        System.out.println("Helllo");
        List<RecipeUnderReview> recipesUnderReview = recipeUnderReviewService.getRecipesUnderReview();
        return ResponseEntity.ok().body(recipesUnderReview);
    }

    @DeleteMapping("/delete/under-review/{recipeId}")
    public ResponseEntity<?> deleteRecipeUnderReview(@PathVariable int recipeId) {
        try {
            recipeUnderReviewService.deleteRecipeUnderReview(recipeId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting recipe: " + e.getMessage());
        }
    }
}
