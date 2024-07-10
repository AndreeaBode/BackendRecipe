package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.dtos.FavoriteDTO;
import sd.entities.LoveReact;
import sd.services.LoveReactService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoveReactControllerTest {

    @Mock
    private LoveReactService loveReactService;

    @InjectMocks
    private LoveReactController loveReactController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void checkIfLiked_True() {
        int userId = 1;
        int recipeId = 1001;

        when(loveReactService.checkIfLiked(userId, recipeId)).thenReturn(true);

        ResponseEntity<Boolean> response = loveReactController.checkIfLiked(userId, recipeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(loveReactService, times(1)).checkIfLiked(userId, recipeId);
    }

    @Test
    void checkIfLiked_False() {
        int userId = 1;
        int recipeId = 1001;

        when(loveReactService.checkIfLiked(userId, recipeId)).thenReturn(false);

        ResponseEntity<Boolean> response = loveReactController.checkIfLiked(userId, recipeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(loveReactService, times(1)).checkIfLiked(userId, recipeId);
    }

    @Test
    void saveLoveReact_Success() {
        Map<String, Object> likeData = new HashMap<>();
        likeData.put("userId", 1);
        likeData.put("recipeId", 1001);
        likeData.put("name", "Test Recipe");

        LoveReact savedLoveReact = new LoveReact();
        savedLoveReact.setUserId(1);
        savedLoveReact.setRecipeId(1001);
        savedLoveReact.setName("Test Recipe");

        when(loveReactService.saveLoveReact(1, 1001, "Test Recipe")).thenReturn(savedLoveReact);

        ResponseEntity<LoveReact> response = loveReactController.saveLoveReact(likeData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedLoveReact, response.getBody());
        verify(loveReactService, times(1)).saveLoveReact(1, 1001, "Test Recipe");
    }

    @Test
    void deleteLoveReact_Success() {
        int userId = 1;
        int recipeId = 1001;
        String name = "Test Recipe";

        ResponseEntity<?> response = loveReactController.deleteLoveReact(userId, recipeId, name);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(loveReactService, times(1)).deleteLoveReact(userId, recipeId, name);
    }

    @Test
    void favorite_NotEmpty() {
        int userId = 1;

        List<FavoriteDTO> favoriteDTOList = new ArrayList<>();
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setRecipeId(1001);
        favoriteDTO.setTitle("Favorite Recipe");
        favoriteDTO.setImage("image-url");
        favoriteDTOList.add(favoriteDTO);

        when(loveReactService.favorite(userId)).thenReturn(favoriteDTOList);

        ResponseEntity<List<FavoriteDTO>> response = loveReactController.favorite(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(favoriteDTOList, response.getBody());
        verify(loveReactService, times(1)).favorite(userId);
    }

    @Test
    void favorite_Empty() {
        int userId = 1;

        when(loveReactService.favorite(userId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<FavoriteDTO>> response = loveReactController.favorite(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(loveReactService, times(1)).favorite(userId);
    }


}
