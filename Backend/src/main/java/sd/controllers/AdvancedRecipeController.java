package sd.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.entities.SearchFoodOptions;
import sd.services.AdvancedRecipeService;

@RestController
@RequestMapping(value = "/")
public class AdvancedRecipeController {
    private final AdvancedRecipeService advancedRecipeService;

    public AdvancedRecipeController(AdvancedRecipeService advancedRecipeService) {
        this.advancedRecipeService = advancedRecipeService;
    }

    @GetMapping("/searchFoodAdvanced")
    public ResponseEntity<String> searchFoodAdvanced(@ModelAttribute SearchFoodOptions options, @RequestParam int userId) {
        ResponseEntity<String> responseEntity = advancedRecipeService.searchFoodAdvanced(options, userId);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @GetMapping("/recipe/{id}/nutrition")
    public ResponseEntity<String> getRecipeNutrition(@PathVariable int id) {

        ResponseEntity<String> responseEntity = advancedRecipeService.getRecipeNutrition(id);
        String responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        return ResponseEntity.ok(responseBody);
    }
}
