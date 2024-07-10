package sd.services;

import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sd.dtos.AddedRecipeDTO;
import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.dtos.builders.ExtractedRecipeBuilder;
import sd.entities.*;
import sd.entities.RecipeUnderReview;
import sd.repositories.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ExtractedRecipeRepository extractedRecipeRepository;
    @Autowired
    private RecipeUnderReviewRepository recipeUnderReviewRepository;

    @Autowired
    private AddedRecipeRepository addedRecipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private StepRepository stepRepository;

    //private final String spoonacularApiKey = "d79ab01d2eff47d081feed07a650ff00";
   //private final String spoonacularApiKey = "da3a95ad9e794dc3b5d43cf1f0f8cf60";
   private final String spoonacularApiKey = "e0f0174758e74ee18fe3567c329272b5";
   //private final String spoonacularApiKey = "08a193a93a2c4a05b3f79421651dd8a7";
    private final String searchApiUrl = "https://api.spoonacular.com/recipes/complexSearch";
    private final String randomApiUrl = "https://api.spoonacular.com/recipes/random";
    private final String recipeInformationApiUrl = "https://api.spoonacular.com/recipes/";
    private final String ingredientsSearchApiUrl = "https://api.spoonacular.com/recipes/findByIngredients";
    private final String menuItemsSearchApiUrl = "https://api.spoonacular.com/food/menuItems/search";

    private final String nutritionWidgetUrl = "https://api.spoonacular.com/recipes/{id}/nutritionWidget.json";

    public ResponseEntity<String> searchFood(String query) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(searchApiUrl)
                    .queryParam("apiKey", spoonacularApiKey)
                    .queryParam("query", query)
                    .queryParam("number", 21);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.toUriString(), String.class);

           // parseResponseAndSaveToDatabase(responseEntity.getBody());
            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody);

            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the request.");
        }
    }


    private void parseResponseAndSaveToDatabase(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            List<Recipe> recipes = new ArrayList<>();

            if (root.has("results")) {
                JsonNode resultsNode = root.get("results");
                for (JsonNode recipeNode : resultsNode) {
                    Recipe recipe = new Recipe();
                    recipe.setId(recipeNode.get("id").asInt());
                    recipe.setTitle(recipeNode.get("title").asText());
                    recipe.setImage(recipeNode.get("image").asText());
                    System.out.println(recipe.toString());
                    recipes.add(recipe);
                }
            }

            recipeRepository.saveAll(recipes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<String> getRandomRecipes() {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(randomApiUrl)
                    .queryParam("apiKey", spoonacularApiKey)
                    .queryParam("number", 100);

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForEntity(builder.toUriString(), String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the request.");
        }
    }

    public ResponseEntity<String> getRecipeInformation(int id, boolean includeNutrition) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(recipeInformationApiUrl + id + "/information")
                    .queryParam("apiKey", spoonacularApiKey)
                    .queryParam("includeNutrition", includeNutrition);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.toUriString(), String.class);

            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody);

            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the request.");
        }
    }


    public ResponseEntity<String> findByIngredients(String ingredients, int ranking) {
        try {
            // Format ingredientele corect pentru a evita probleme de interpretare
            String formattedIngredients = Arrays.stream(ingredients.split(","))
                    .map(String::trim)
                    .collect(Collectors.joining(","));

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ingredientsSearchApiUrl)
                    .queryParam("apiKey", spoonacularApiKey)
                    .queryParam("ingredients", formattedIngredients)
                    .queryParam("number", 30)
                    .queryParam("limitLicense", true)
                    .queryParam("ranking", ranking)
                    .queryParam("ignorePantry", true); // Modificat pentru a ignora articolele tipice din cămară

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.toUriString(), String.class);

            String responseBody = responseEntity.getBody();
            System.out.println("Response Body: " + responseBody);

            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the request.");
        }
    }


    public ResponseEntity<String> searchMenuItems(String query) {
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(menuItemsSearchApiUrl)
                    .queryParam("apiKey", spoonacularApiKey)
                    .queryParam("query", query)
                    .queryParam("number", 1); // Adjust the number as needed

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForEntity(builder.toUriString(), String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing the request.");
        }
    }

}

