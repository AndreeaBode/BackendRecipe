package sd.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import sd.dtos.ExtractedDTO;
import sd.dtos.ExtractedRecipeDTO;
import sd.entities.ExtractedRecipe;
import sd.repositories.ExtractedRecipeRepository;
import sd.repositories.IngredientRepository;
import sd.repositories.StepRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExtractedRecipeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ExtractedRecipeRepository extractedRecipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private StepRepository stepRepository;

    @Mock
    private NewsletterService newsletterService;

    @InjectMocks
    private ExtractedRecipeService extractedRecipeService;

    private final String testApiKey = "test-api-key";

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(extractedRecipeService, "spoonacularApiKey", testApiKey);
    }

    @Test
    public void testGetRecipeTitleFromSpoonacular() {
        String url = "https://www.dishgen.com/recipes/creamy-vegan-fettuccine-with-cashew-sauce-lgnye9ct";
        String expectedTitle = "Creamy Vegan Fettuccine with Cashew Sauce";

        String mockResponse = "{\"title\": \"" + expectedTitle + "\"}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        String actualTitle = extractedRecipeService.getRecipeTitleFromSpoonacular(url);

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    @Disabled
    public void testExtractRecipe_Success() {
        String url = "https://www.dishgen.com/recipes/creamy-vegan-fettuccine-with-cashew-sauce-lgnye9ct";
        String mockResponse = "{\"title\": \"Test Recipe\", \"image\": \"example.jpg\", \"extendedIngredients\": [], \"analyzedInstructions\": []}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(mockResponse);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);
        when(extractedRecipeRepository.existsByTitle(anyString())).thenReturn(false);
        when(extractedRecipeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<String> result = extractedRecipeService.extractRecipe(url);

        assertEquals(responseEntity, result);
        verify(extractedRecipeRepository, times(1)).save(any());
        verify(ingredientRepository, times(1)).save(any());
        verify(stepRepository, times(1)).save(any());
    }



    @Test(expected = RuntimeException.class)
    public void testGetDetailDisghen_NonExistingRecipe() {
        int id = 999;

        when(extractedRecipeRepository.findById(id)).thenReturn(Optional.empty());

        extractedRecipeService.getDetailDisghen(id);
    }

}
