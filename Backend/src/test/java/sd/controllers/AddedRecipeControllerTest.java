package sd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.dtos.AddedRecipeDTO;
import sd.entities.RecipeUnderReview;
import sd.services.AddedRecipeService;
import sd.services.NewsletterService;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddedRecipeControllerTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private NewsletterService newsletterService;

    @Mock
    private AddedRecipeService addedRecipeService;

    @InjectMocks
    private AddedRecipeController addedRecipeController;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        addedRecipeController = new AddedRecipeController(newsletterService, addedRecipeService);

        try {
            Field objectMapperField = AddedRecipeController.class.getDeclaredField("objectMapper");
            objectMapperField.setAccessible(true);
            objectMapperField.set(addedRecipeController, objectMapper);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAddedRecipesDetail() {
        List<AddedRecipeDTO> mockRecipes = Collections.singletonList(new AddedRecipeDTO());
        when(addedRecipeService.getAllAddedRecipes()).thenReturn(mockRecipes);

        List<AddedRecipeDTO> recipes = addedRecipeController.getAddedRecipesDetail();

        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        verify(addedRecipeService, times(1)).getAllAddedRecipes();
    }

    @Test
    public void testSubmitRecipe_Success() {
        RecipeUnderReview mockRecipe = new RecipeUnderReview();
        when(addedRecipeService.submitRecipeForApproval(any(RecipeUnderReview.class))).thenReturn(mockRecipe);

        ResponseEntity<RecipeUnderReview> response = addedRecipeController.submitRecipe(mockRecipe);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(addedRecipeService, times(1)).submitRecipeForApproval(any(RecipeUnderReview.class));
    }

    @Test
    public void testSubmitRecipe_Failure() {
        when(addedRecipeService.submitRecipeForApproval(any(RecipeUnderReview.class))).thenReturn(null);

        RecipeUnderReview recipe = new RecipeUnderReview();
        ResponseEntity<RecipeUnderReview> response = addedRecipeController.submitRecipe(recipe);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(addedRecipeService, times(1)).submitRecipeForApproval(any(RecipeUnderReview.class));
    }

    @Test
    public void testGetAddedDetailRecipe() {
        AddedRecipeDTO mockRecipe = new AddedRecipeDTO();
        when(addedRecipeService.getAddedDetailRecipe(1)).thenReturn(mockRecipe);

        ResponseEntity<AddedRecipeDTO> response = addedRecipeController.getAddedDetailRecipe(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(addedRecipeService, times(1)).getAddedDetailRecipe(1);
    }

    @Test
    public void testAddRecipe_Success() throws Exception {
        String payload = "{\"title\":\"Test Recipe\"}";
        AddedRecipeDTO mockRecipe = new AddedRecipeDTO();
        when(objectMapper.readValue(payload, AddedRecipeDTO.class)).thenReturn(mockRecipe);
        when(addedRecipeService.addRecipe(any(AddedRecipeDTO.class))).thenReturn(mockRecipe);

        ResponseEntity<AddedRecipeDTO> response = addedRecipeController.addRecipe(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(objectMapper, times(1)).readValue(payload, AddedRecipeDTO.class);
        verify(addedRecipeService, times(1)).addRecipe(any(AddedRecipeDTO.class));
    }

    @Test
    public void testAddRecipe_Failure() throws Exception {
        String payload = "{\"title\":\"Test Recipe\"}";
        when(objectMapper.readValue(payload, AddedRecipeDTO.class)).thenThrow(new RuntimeException());

        ResponseEntity<AddedRecipeDTO> response = addedRecipeController.addRecipe(payload);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(objectMapper, times(1)).readValue(payload, AddedRecipeDTO.class);
        verify(addedRecipeService, times(0)).addRecipe(any(AddedRecipeDTO.class));
    }
}
