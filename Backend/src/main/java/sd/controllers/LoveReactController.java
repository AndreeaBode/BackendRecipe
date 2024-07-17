package sd.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sd.dtos.FavoriteDTO;
import sd.dtos.AddedRecipeDTO;
import sd.entities.LoveReact;
import sd.services.LoveReactService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/love")
public class LoveReactController {

    private final LoveReactService loveReactService;

    public LoveReactController(LoveReactService loveReactService) {
        this. loveReactService =  loveReactService;
    }


    @GetMapping("/isLiked/{userId}/{recipeId}")
    public ResponseEntity<Boolean> checkIfLiked(@PathVariable int userId, @PathVariable int recipeId) {
        boolean isLiked = loveReactService.checkIfLiked(userId, recipeId);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }


    @PostMapping("/likes")
    public ResponseEntity<LoveReact> saveLoveReact(@RequestBody Map<String, Object> likeData) {
        System.out.println();
        System.out.println("Like "+ likeData);
        System.out.println();
        int userId = (int) likeData.get("userId");
        int recipeId = (int) likeData.get("recipeId");
        String recipeName = (String) likeData.get("name");
        System.out.println("User" + " " + userId);
        System.out.println("Recipe" + " " + recipeId);
        LoveReact savedLoveReact = loveReactService.saveLoveReact(userId, recipeId, recipeName);

        try {
            return new ResponseEntity<>(savedLoveReact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/likes/{userId}/{recipeId}/{name}")
    public ResponseEntity<?> deleteLoveReact(@PathVariable int userId, @PathVariable int recipeId, @PathVariable String name) { // Adăugăm și numele rețetei ca parametru în metoda
        System.out.println("ÜserId " + userId +  " RecipeId " + recipeId);
        loveReactService.deleteLoveReact(userId, recipeId, name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/favorite/{userId}")
    public ResponseEntity<List<FavoriteDTO>> favorite(@PathVariable int userId) {
        List<FavoriteDTO> favoriteRecipes = loveReactService.favorite(userId);
        if (!favoriteRecipes.isEmpty()) {
            return ResponseEntity.ok(favoriteRecipes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
