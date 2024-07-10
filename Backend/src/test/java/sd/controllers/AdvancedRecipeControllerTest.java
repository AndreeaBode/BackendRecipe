package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.entities.SearchFoodOptions;
import sd.services.AdvancedRecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class AdvancedRecipeControllerTest {

    @Mock
    private AdvancedRecipeService advancedRecipeService;

    @InjectMocks
    private AdvancedRecipeController advancedRecipeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchFoodAdvanced() {
        // Mocking
        SearchFoodOptions options = new SearchFoodOptions();
        int userId = 1;
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Mock response body", HttpStatus.OK);
        when(advancedRecipeService.searchFoodAdvanced(eq(options), eq(userId))).thenReturn(mockResponse);

        ResponseEntity<String> response = advancedRecipeController.searchFoodAdvanced(options, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mock response body", response.getBody());
    }

    @Test
    public void testGetRecipeNutrition() {

        int recipeId = 123;
        ResponseEntity<String> mockResponse = new ResponseEntity<>("Mock nutrition data", HttpStatus.OK);
        when(advancedRecipeService.getRecipeNutrition(eq(recipeId))).thenReturn(mockResponse);

        ResponseEntity<String> response = advancedRecipeController.getRecipeNutrition(recipeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mock nutrition data", response.getBody());
    }
}
