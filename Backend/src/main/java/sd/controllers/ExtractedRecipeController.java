package sd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.services.ExtractedRecipeService;
import sd.services.NewsletterService;

import java.util.List;
@RestController
@RequestMapping(value = "/")

public class ExtractedRecipeController {

    private final ExtractedRecipeService extractedRecipeService;
    private final NewsletterService newsletterService;


    public ExtractedRecipeController(ExtractedRecipeService extractedRecipeService, NewsletterService newsletterService) {
        this.extractedRecipeService = extractedRecipeService;
        this.newsletterService = newsletterService;
    }

    @GetMapping("/extractRecipe")
    public ResponseEntity<String> extractRecipe(@RequestParam String url) {
        ResponseEntity<String> responseEntity = extractedRecipeService.extractRecipe(url);
        String responseBody = responseEntity.getBody();
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/dishgen-detail/{id}")
    public ResponseEntity<ExtractedDTO> getDetailDisghen(@PathVariable("id") int id) {
        ExtractedDTO extractedDTO = extractedRecipeService.getDetailDisghen(id);
        return new ResponseEntity<>(extractedDTO, HttpStatus.OK);
    }


    @GetMapping("/recipes")
    public List<ExtractedRecipeDTO> getAllRecipes() {
        List<ExtractedRecipeDTO> recipes = extractedRecipeService.getAllRecipes();
        System.out.println("Recipe" + recipes);
        return recipes;
    }
    @GetMapping("/recipes-detail")
    public List<ExtractedRecipeDTO> getRecipesDetail() {
        List<ExtractedRecipeDTO> recipes = extractedRecipeService.getAllRecipes();
        System.out.println("Recipe" + recipes);
        return recipes;
    }
}
