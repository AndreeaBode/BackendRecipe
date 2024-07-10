package sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sd.dtos.AddedRecipeDTO;
import sd.dtos.builders.AddedRecipeBuilder;
import sd.entities.*;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.RecipeUnderReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddedRecipeService {

    @Autowired
    private RecipeUnderReviewRepository recipeUnderReviewRepository;
    @Autowired
    private AddedRecipeRepository addedRecipeRepository;

    @Autowired
    private NewsletterService newsletterService;

    public List<AddedRecipeDTO> getAllAddedRecipes() {
        List<AddedRecipe> addedRecipes = addedRecipeRepository.findAll();
        return addedRecipes.stream()
                .map(AddedRecipeBuilder::buildDTO)
                .collect(Collectors.toList());
    }

    public RecipeUnderReview submitRecipeForApproval(RecipeUnderReview recipe) {
        try {

            String title = recipe.getTitle();
            String image = recipe.getImage();
            List<RecipeUnderReviewIngredient> ingredients = recipe.getIngredients();
            List<RecipeUnderReviewStep> instructions = recipe.getInstructions();

            saveRecipeUnderReview(title, image, ingredients, instructions);

            return recipe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding recipe: " + e.getMessage());
        }
    }

    private ResponseEntity<String> saveRecipeUnderReview(String title, String image, List<RecipeUnderReviewIngredient> ing, List<RecipeUnderReviewStep> instructions) {
        try {
                if (addedRecipeRepository.existsByTitle(title)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("A recipe with the same title already exists.");
                }

            RecipeUnderReview recipeUnderReview = new RecipeUnderReview();
            recipeUnderReview.setTitle(title);
            recipeUnderReview.setImage(image);

            List<RecipeUnderReviewIngredient> ingredients = ing;

            if (!ingredients.isEmpty()) {
                RecipeUnderReviewIngredient ultimulElement = ingredients.get(ingredients.size() - 1);
                ingredients.remove(ingredients.size() - 1);
                ingredients.add(0, ultimulElement);
                System.out.println("Ing" + "  "+ingredients.toString());
            } else {
                System.out.println("Lista de ingrediente este goală.");
            }
            List<RecipeUnderReviewStep> step = instructions;

            if (!step.isEmpty()) {
                RecipeUnderReviewStep ultimulElement = step.get(step.size() - 1);
                step.remove(step.size() - 1);
                step.add(0, ultimulElement);
                System.out.println("Ing" + "  "+step);
            } else {
                System.out.println("Lista de ingrediente este goală.");
            }
            for (RecipeUnderReviewIngredient ingredient : ingredients) {
                ingredient.setRecipe(recipeUnderReview);
            }

            for (RecipeUnderReviewStep instruction : step) {
                instruction.setRecipe(recipeUnderReview);
            }

            recipeUnderReview.setIngredients(ingredients);
            recipeUnderReview.setInstructions(step);
            recipeUnderReviewRepository.save(recipeUnderReview);
            return ResponseEntity.ok("Recipe saved successfully");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving recipe: " + e.getMessage());
        }
    }
    public AddedRecipeDTO getAddedDetailRecipe(int id) {
        Optional<AddedRecipe> addedRecipeOptional = addedRecipeRepository.findById(id);
        if (addedRecipeOptional.isPresent()) {
            AddedRecipe addedRecipe = addedRecipeOptional.get();
            addedRecipe.getIngredients().size();
            addedRecipe.getSteps().size();
            return new AddedRecipeDTO(addedRecipe);
        } else {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
    }
    public AddedRecipeDTO addRecipe(AddedRecipeDTO recipeDTO) {
        try {

            String title = recipeDTO.getTitle();
            String image = recipeDTO.getImage();
            List<AddedIngredient> ingredients = recipeDTO.getIngredients();
            List<AddedStep> instructions = recipeDTO.getInstructions();

            saveRecipe(title, image, ingredients, instructions);

            return recipeDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding recipe: " + e.getMessage());
        }
    }

    private void saveRecipe(String title, String image, List<AddedIngredient> ing, List<AddedStep> instructions) {
        try {
            AddedRecipe addedRecipe = new AddedRecipe();
            addedRecipe.setTitle(title);
            addedRecipe.setImage(image);

            List<AddedIngredient> ingredients = ing;

            List<AddedStep> steps = instructions;

            for (AddedIngredient ingredient : ingredients) {
                ingredient.setRecipe(addedRecipe);
            }

            for (AddedStep instruction : steps) {
                instruction.setRecipe(addedRecipe);
            }

            addedRecipe.setIngredients(ingredients);
            addedRecipe.setSteps(steps);

            addedRecipeRepository.save(addedRecipe);
            newsletterService.sendEmail(addedRecipe.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving recipe: " + e.getMessage());
        }
    }

}
