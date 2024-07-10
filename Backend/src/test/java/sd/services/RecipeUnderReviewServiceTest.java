package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sd.entities.RecipeUnderReview;
import sd.repositories.RecipeUnderReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeUnderReviewServiceTest {

    @Mock
    private RecipeUnderReviewRepository recipeUnderReviewRepository;

    @InjectMocks
    private RecipeUnderReviewService recipeUnderReviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecipesUnderReview() {
        List<RecipeUnderReview> mockRecipeList = new ArrayList<>();
        RecipeUnderReview recipeUnderReview = new RecipeUnderReview();
        recipeUnderReview.setId(1);
        recipeUnderReview.setTitle("Test Recipe");
        recipeUnderReview.setImage("image-url");
        mockRecipeList.add(recipeUnderReview);

        when(recipeUnderReviewRepository.findAll()).thenReturn(mockRecipeList);

        List<RecipeUnderReview> result = recipeUnderReviewService.getRecipesUnderReview();

        assertEquals(mockRecipeList.size(), result.size());
        assertEquals(mockRecipeList.get(0).getTitle(), result.get(0).getTitle());
        verify(recipeUnderReviewRepository, times(1)).findAll();
    }

    @Test
    void testDeleteRecipeUnderReview() {
        int recipeId = 1;
        RecipeUnderReview mockRecipe = new RecipeUnderReview();
        mockRecipe.setId(recipeId);
        mockRecipe.setTitle("Test Recipe");
        mockRecipe.setImage("image-url");

        when(recipeUnderReviewRepository.findById(recipeId)).thenReturn(Optional.of(mockRecipe));

        recipeUnderReviewService.deleteRecipeUnderReview(recipeId);

        verify(recipeUnderReviewRepository, times(1)).delete(mockRecipe);
    }

    @Test
    void testDeleteRecipeUnderReview_NotFound() {
        int recipeId = 1;

        when(recipeUnderReviewRepository.findById(recipeId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> recipeUnderReviewService.deleteRecipeUnderReview(recipeId));

        assertEquals("Recipe not found with id: " + recipeId, exception.getMessage());
        verify(recipeUnderReviewRepository, never()).delete(any());
    }

}
