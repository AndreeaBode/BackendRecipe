package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.entities.RecipeUnderReview;
import sd.services.RecipeUnderReviewService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeUnderReviewControllerTest {

    @Mock
    private RecipeUnderReviewService recipeUnderReviewService;

    @InjectMocks
    private RecipeUnderReviewController recipeUnderReviewController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRecipesUnderReview_Success() {
        List<RecipeUnderReview> mockRecipeList = new ArrayList<>();
        RecipeUnderReview recipeUnderReview = new RecipeUnderReview();
        recipeUnderReview.setId(1);
        recipeUnderReview.setTitle("Test Recipe");
        recipeUnderReview.setImage("image-url");


        when(recipeUnderReviewService.getRecipesUnderReview()).thenReturn(mockRecipeList);

        ResponseEntity<List<RecipeUnderReview>> response = recipeUnderReviewController.getRecipesUnderReview();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRecipeList, response.getBody());
        verify(recipeUnderReviewService, times(1)).getRecipesUnderReview();
    }

    @Test
    void deleteRecipeUnderReview_Success() {
        int recipeId = 1;

        ResponseEntity<?> response = recipeUnderReviewController.deleteRecipeUnderReview(recipeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(recipeUnderReviewService, times(1)).deleteRecipeUnderReview(recipeId);
    }

    @Test
    void deleteRecipeUnderReview_InternalServerError() {
        int recipeId = 1;

        doThrow(new RuntimeException("Recipe not found")).when(recipeUnderReviewService).deleteRecipeUnderReview(recipeId);

        ResponseEntity<?> response = recipeUnderReviewController.deleteRecipeUnderReview(recipeId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting recipe: Recipe not found", response.getBody());
        verify(recipeUnderReviewService, times(1)).deleteRecipeUnderReview(recipeId);
    }

}
