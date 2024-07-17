package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sd.dtos.AddedRecipeDTO;
import sd.entities.AddedIngredient;
import sd.entities.AddedRecipe;
import sd.entities.AddedStep;
import sd.entities.RecipeUnderReview;
import sd.repositories.AddedRecipeRepository;
import sd.repositories.RecipeUnderReviewRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddedRecipeServiceTest {

    @Mock
    private RecipeUnderReviewRepository recipeUnderReviewRepository;

    @Mock
    private AddedRecipeRepository addedRecipeRepository;

    @Mock
    private NewsletterService newsletterService;

    @InjectMocks
    private AddedRecipeService addedRecipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAddedRecipes() {
        List<AddedRecipe> mockRecipes = Collections.singletonList(new AddedRecipe());
        when(addedRecipeRepository.findAll()).thenReturn(mockRecipes);

        List<AddedRecipeDTO> recipes = addedRecipeService.getAllAddedRecipes();

        assertNotNull(recipes);
        assertEquals(1, recipes.size());
        verify(addedRecipeRepository, times(1)).findAll();
    }
//
//    @Test
//    public void testSubmitRecipeForApproval_Success() {
//        RecipeUnderReview mockRecipe = new RecipeUnderReview();
//        when(recipeUnderReviewRepository.save(any(RecipeUnderReview.class))).thenReturn(mockRecipe);
//
//        RecipeUnderReview result = addedRecipeService.submitRecipeForApproval(mockRecipe);
//
//        assertNotNull(result);
//        verify(recipeUnderReviewRepository, times(1)).save(any(RecipeUnderReview.class));
//    }


    @Test
    public void testGetAddedDetailRecipe_Success() {
        AddedRecipe mockRecipe = new AddedRecipe();
        when(addedRecipeRepository.findById(1)).thenReturn(Optional.of(mockRecipe));

        AddedRecipeDTO result = addedRecipeService.getAddedDetailRecipe(1);

        assertNotNull(result);
        verify(addedRecipeRepository, times(1)).findById(1);
    }

    @Test
    public void testGetAddedDetailRecipe_NotFound() {
        when(addedRecipeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            addedRecipeService.getAddedDetailRecipe(1);
        });

        verify(addedRecipeRepository, times(1)).findById(1);
    }
}
