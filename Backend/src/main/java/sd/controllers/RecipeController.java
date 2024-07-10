package sd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.dtos.AddedRecipeDTO;
import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.entities.RecipeUnderReview;
import sd.services.RecipeService;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class RecipeController {

    private final RecipeService recipeService;
    @Autowired
    private ObjectMapper objectMapper;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/searchFood")
    public ResponseEntity<String> searchFood(@RequestParam String query) {
        ResponseEntity<String> responseEntity = recipeService.searchFood(query);
        String responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        return ResponseEntity.ok(responseBody);
    }


    @GetMapping("/randomRecipes")
    public ResponseEntity<String> getRandomRecipes() {
        ResponseEntity<String> responseEntity = recipeService.getRandomRecipes();
        String responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/recipe/{id}/information")
    public ResponseEntity<String> getRecipeInformation(@PathVariable int id, @RequestParam(defaultValue = "false") boolean includeNutrition) {
        ResponseEntity<String> responseEntity = recipeService.getRecipeInformation(id, includeNutrition);
        String responseBody = responseEntity.getBody();

        String cleanResponseBody = responseBody.replaceAll("<[^>]+>", "");

        System.out.println(cleanResponseBody);
        return ResponseEntity.ok(cleanResponseBody);
    }

    @GetMapping("/recipes/findByIngredients")
    public ResponseEntity<String> findRecipesByIngredients(
            @RequestParam("ingredients") String ingredients,
            @RequestParam("ranking") int ranking) {
        ResponseEntity<String> responseEntity = recipeService.findByIngredients(ingredients, ranking);
        String responseBody = responseEntity.getBody();
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/menuItems")
    public ResponseEntity<String> searchMenuItems(
            @RequestParam String query) {
        ResponseEntity<String> responseEntity = recipeService.searchMenuItems(query);
        String responseBody = responseEntity.getBody();
        return ResponseEntity.ok(responseBody);
    }

}

