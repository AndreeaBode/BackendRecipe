package sd.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sd.dtos.FavoriteDTO;
import sd.dtos.AddedRecipeDTO;
import sd.dtos.FavoriteDTO2;
import sd.dtos.builders.Favorite;
import sd.dtos.builders.AddedRecipeBuilder;
import sd.entities.AddedRecipe;
import sd.entities.ExtractedRecipe;
import sd.entities.LoveReact;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.ExtractedRecipeRepository;
import sd.repositories.LoveReactRepository;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoveReactService {

    @Autowired
    private LoveReactRepository loveReactRepository;

    @Autowired
    private AddedRecipeRepository addedRecipeRepository;

    @Autowired
    private ExtractedRecipeRepository extractedRecipeRepository;

    //private final String spoonacularApiKey = "08a193a93a2c4a05b3f79421651dd8a7";
    private final String spoonacularApiKey = "e0f0174758e74ee18fe3567c329272b5";
   // private final String spoonacularApiKey = "d79ab01d2eff47d081feed07a650ff00";
    //private final String spoonacularApiKey = "da3a95ad9e794dc3b5d43cf1f0f8cf60";
    private final String getRecipeInformationURL = "https://api.spoonacular.com/recipes/{id}/information";
    public LoveReact saveLoveReact(int userId, int recipeId, String name) {
        System.out.println("Am ajuuuuuun");
        Optional<LoveReact> loveReactOptional = loveReactRepository.findByUserIdAndRecipeIdAndName(userId, recipeId, name);
        if (loveReactOptional.isPresent()) {
            System.out.println("Combinația de userId și recipeId există deja.");
            return null;
        } else {
            LoveReact loveReact = new LoveReact();
            loveReact.setUserId(userId);
            loveReact.setRecipeId(recipeId);
            loveReact.setName(name);
            return loveReactRepository.save(loveReact);
        }
    }


    public void deleteLoveReact(int userId, int recipeId, String name) {
        System.out.println("Yeyyyyy");
        System.out.println("U"+ userId);
        System.out.println("r"+ recipeId);
        System.out.println("N"+ name);

        Optional<LoveReact> loveReactOptional = loveReactRepository.findByUserIdAndRecipeIdAndName(userId, recipeId,name);
        System.out.println("A" + " " + loveReactOptional);
        if (loveReactOptional.isPresent()) {
            LoveReact loveReact = loveReactOptional.get();
            System.out.println("B" + " " + loveReact);
            loveReactRepository.delete(loveReact);
        } else {
            throw new EntityNotFoundException("LoveReact not found for userId " + userId + " and recipeId " + recipeId + "änd name "+ name);
        }
    }

    public boolean checkIfLiked(int userId, int recipeId) {

        List<LoveReact> loveReactList = loveReactRepository.findByUserIdAndRecipeId(userId, recipeId);

        if (!loveReactList.isEmpty()) {
            Set<String> uniqueRecipeNames = new HashSet<>();


            for (LoveReact loveReact : loveReactList) {
                uniqueRecipeNames.add(loveReact.getName());
            }

            if (uniqueRecipeNames.size() > 1) {
                return true;
            } else {
                return true;
            }
        } else {

            return false;
        }
    }



    public List<FavoriteDTO> favorite(int userId) {
        List<LoveReact> loveReacts = loveReactRepository.findByUserId(userId);

        System.out.println("LoveReacts " + loveReacts.toString() );

        List<FavoriteDTO> favoriteRecipes = new ArrayList<>();

        loveReacts.forEach(loveReact -> {
            String name = loveReact.getName();
            int recipeId = loveReact.getRecipeId();

            Optional<?> recipeOptional = null;
            if (name.equals("extracted_recipes")) {
                recipeOptional = extractedRecipeRepository.findById(recipeId);
                System.out.println(recipeOptional.toString());
            }
            if (name.equals("added_recipes")) {
                recipeOptional = addedRecipeRepository.findById(recipeId);
                System.out.println(recipeOptional);
            }
            if (name.equals("spoonacular")) {
                try {
                    String spoonacularUrl = "https://api.spoonacular.com/recipes/" + recipeId + "/information";

                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(spoonacularUrl)
                            .queryParam("apiKey", spoonacularApiKey)
                            .queryParam("includeNutrition", false); // Excludem datele de nutriție


                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(builder.toUriString(), String.class);


                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        String responseBody = responseEntity.getBody();
                        System.out.println("Response Body: " + responseBody);

                        FavoriteDTO recipeDTO = parseResponse(responseBody);
                        recipeDTO.setName(name);
                        recipeDTO.setRecipeId(recipeId);

                        favoriteRecipes.add(recipeDTO);
                        System.out.println();
                        System.out.println();
                        System.out.println("bbb" + recipeDTO);
                        System.out.println();
                        System.out.println();
                    } else {
                        System.err.println("Cererea către serviciul Spoonacular a eșuat: " + responseEntity.getStatusCode());
                    }
                } catch (RestClientException e) {
                    throw new RuntimeException(e);
                }
            }
            if (recipeOptional != null && recipeOptional.isPresent()) {
                Object recipe = recipeOptional.get();
                if (recipe instanceof ExtractedRecipe) {
                    System.out.println("Rep " + recipe);
                    favoriteRecipes.add(Favorite.buildDTOExtracted((ExtractedRecipe) recipe, "extracted_recipes"));
                } else if (recipe instanceof AddedRecipe) {
                    favoriteRecipes.add(Favorite.buildDTOAdded((AddedRecipe) recipe, "added_recipes"));
                }
            }
        });

        System.out.println("Favorite " + favoriteRecipes);
        return favoriteRecipes;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    public FavoriteDTO parseResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            String title = rootNode.get("title").asText();
            String image = rootNode.get("image").asText();
            System.out.println();
            System.out.println("Title " + title + "Image " + image);
            System.out.println();
            FavoriteDTO favoriteDTO = new FavoriteDTO();
            favoriteDTO.setTitle(title);
            favoriteDTO.setImage(image);
            return favoriteDTO;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Eroare la parsarea răspunsului JSON", e);
        }
    }


}
