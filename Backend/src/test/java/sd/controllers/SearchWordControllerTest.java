package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sd.dtos.ExtractedRecipeDTO;
import sd.services.SearchWordService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SearchWordControllerTest {

    @Mock
    private SearchWordService searchWordService;

    @InjectMocks
    private SearchWordController searchWordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchRecipesByDishType() {
        String dishType = "breakfast";
        List<ExtractedRecipeDTO> expectedRecipes = new ArrayList<>();
        ExtractedRecipeDTO recipe1 = new ExtractedRecipeDTO(1, "Recipe 1", "image-url-1");
        ExtractedRecipeDTO recipe2 = new ExtractedRecipeDTO(2, "Recipe 2", "image-url-2");
        expectedRecipes.add(recipe1);
        expectedRecipes.add(recipe2);

        when(searchWordService.searchByDishType(anyString())).thenReturn(expectedRecipes);

        List<ExtractedRecipeDTO> actualRecipes = searchWordController.searchRecipesByDishType(dishType);

        assertEquals(expectedRecipes.size(), actualRecipes.size());
        assertEquals(expectedRecipes.get(0), actualRecipes.get(0));
        assertEquals(expectedRecipes.get(1), actualRecipes.get(1));
    }

    @Test
    void testSearchRecipesByDiet() {
        String diet = "vegetarian";
        List<ExtractedRecipeDTO> expectedRecipes = new ArrayList<>();
        ExtractedRecipeDTO recipe1 = new ExtractedRecipeDTO(1, "Recipe 1", "image-url-1");
        ExtractedRecipeDTO recipe2 = new ExtractedRecipeDTO(2, "Recipe 2", "image-url-2");
        expectedRecipes.add(recipe1);
        expectedRecipes.add(recipe2);

        when(searchWordService.searchByDiet(anyString())).thenReturn(expectedRecipes);

        List<ExtractedRecipeDTO> actualRecipes = searchWordController.searchRecipesByDiet(diet);

        assertEquals(expectedRecipes.size(), actualRecipes.size());
        assertEquals(expectedRecipes.get(0), actualRecipes.get(0));
        assertEquals(expectedRecipes.get(1), actualRecipes.get(1));
    }
}
