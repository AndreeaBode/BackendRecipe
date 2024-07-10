package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.services.ExtractedRecipeService;
import sd.services.NewsletterService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtractedRecipeControllerTest {

    @Mock
    private ExtractedRecipeService extractedRecipeService;

    @Mock
    private NewsletterService newsletterService;

    @InjectMocks
    private ExtractedRecipeController controller;

    @BeforeEach
    void setUp() {
        reset(extractedRecipeService, newsletterService);
    }

    @Test
    void testExtractRecipe() {
        String url = "https://example.com/recipe";
        String responseBody = "Extracted recipe details";

        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok(responseBody);
        when(extractedRecipeService.extractRecipe(url)).thenReturn(mockResponseEntity);

        ResponseEntity<String> responseEntity = controller.extractRecipe(url);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseBody, responseEntity.getBody());

        verify(extractedRecipeService, times(1)).extractRecipe(url);
    }

    @Test
    void testGetDetailDisghen() {
        int id = 1;
        ExtractedDTO expectedDTO = new ExtractedDTO();

        when(extractedRecipeService.getDetailDisghen(id)).thenReturn(expectedDTO);

        ResponseEntity<ExtractedDTO> responseEntity = controller.getDetailDisghen(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDTO, responseEntity.getBody());

        verify(extractedRecipeService, times(1)).getDetailDisghen(id);
    }

    @Test
    void testGetAllRecipes() {
        List<ExtractedRecipeDTO> expectedRecipes = new ArrayList<>();

        when(extractedRecipeService.getAllRecipes()).thenReturn(expectedRecipes);

        List<ExtractedRecipeDTO> recipes = controller.getAllRecipes();

        assertEquals(expectedRecipes.size(), recipes.size());

        verify(extractedRecipeService, times(1)).getAllRecipes();
    }
}
