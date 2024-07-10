package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sd.dtos.ExtractedRecipeDTO;
import sd.entities.ExtractedRecipe;
import sd.repositories.ExtractedRecipeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SearchWordServiceTest {

    @Mock
    private ExtractedRecipeRepository extractedRecipeRepository;

    @InjectMocks
    private SearchWordService searchWordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchByDishType() {
        // Given
        String dishType = "breakfast";
        List<ExtractedRecipe> recipes = new ArrayList<>();
        recipes.add(new ExtractedRecipe(1, "Recipe 1", "image-url-1", List.of(dishType), List.of("vegetarian")));
        recipes.add(new ExtractedRecipe(2, "Recipe 2", "image-url-2", List.of(dishType), List.of("vegan")));

        when(extractedRecipeRepository.findByDishTypesContaining(anyString())).thenReturn(recipes);

        // When
        List<ExtractedRecipeDTO> result = searchWordService.searchByDishType(dishType);

        // Then
        assertEquals(recipes.size(), result.size());
        assertEquals(recipes.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(recipes.get(1).getTitle(), result.get(1).getTitle());
    }

    @Test
    void testSearchByDiet() {
        String diet = "vegetarian";
        List<ExtractedRecipe> recipes = new ArrayList<>();
        ExtractedRecipe recipe1 = new ExtractedRecipe(1, "Recipe 1", "image-url-1", new ArrayList<>(), List.of(diet));
        ExtractedRecipe recipe2 = new ExtractedRecipe(2, "Recipe 2", "image-url-2", new ArrayList<>(), List.of(diet));
        recipes.add(recipe1);
        recipes.add(recipe2);

        when(extractedRecipeRepository.findByDietsContaining(anyString())).thenReturn(recipes);

        List<ExtractedRecipeDTO> result = searchWordService.searchByDiet(diet);

        assertEquals(recipes.size(), result.size());
        assertEquals(recipe1.getTitle(), result.get(0).getTitle());
        assertEquals(recipe2.getTitle(), result.get(1).getTitle());
    }
}
