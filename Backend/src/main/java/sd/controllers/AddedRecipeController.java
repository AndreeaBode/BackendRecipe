package sd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.dtos.AddedRecipeDTO;
import sd.entities.RecipeUnderReview;
import sd.services.AddedRecipeService;
import sd.services.NewsletterService;

import java.util.List;

@RestController
@RequestMapping(value = "/")

public class AddedRecipeController {

    @Autowired
    private ObjectMapper objectMapper;

    private final NewsletterService newsletterService;
    private final AddedRecipeService addedRecipeService;

    public AddedRecipeController(NewsletterService newsletterService, AddedRecipeService addedRecipeService) {
        this.newsletterService = newsletterService;
        this.addedRecipeService = addedRecipeService;
    }

    @GetMapping("/love/added-recipe-detail")
    public List<AddedRecipeDTO> getAddedRecipesDetail() {
        List<AddedRecipeDTO> recipes = addedRecipeService.getAllAddedRecipes();
        System.out.println("Recipe" + recipes);
        return recipes;
    }

    @PostMapping("/submit")
    public ResponseEntity<RecipeUnderReview> submitRecipe(@RequestBody RecipeUnderReview recipe) {
        try {
            addedRecipeService.submitRecipeForApproval(recipe);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/added-detail/{id}")
    public ResponseEntity<AddedRecipeDTO> getAddedDetailRecipe(@PathVariable("id") int id) {
        AddedRecipeDTO addedRecipeDTO = addedRecipeService.getAddedDetailRecipe(id);
        return new ResponseEntity<>(addedRecipeDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddedRecipeDTO> addRecipe(@RequestBody String payload) {
        try {
            AddedRecipeDTO recipeDTO = objectMapper.readValue(payload, AddedRecipeDTO.class);
            AddedRecipeDTO addedRecipeDTO = addedRecipeService.addRecipe(recipeDTO);

            try {
                return ResponseEntity.ok(addedRecipeDTO);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
