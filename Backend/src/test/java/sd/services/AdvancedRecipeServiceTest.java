package sd.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sd.entities.SearchFoodOptions;
import sd.entities.User;
import sd.repositories.RecipeRepository;
import sd.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class AdvancedRecipeServiceTest {

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @InjectMocks
    private AdvancedRecipeService advancedRecipeService;

    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @Disabled
    void testSearchFoodAdvanced_WithValidUser() throws Exception {
        SearchFoodOptions options = new SearchFoodOptions();
        options.setQuery("chicken");
        User user = new User();
        user.setId(1);
        user.setRole("User");
        user.setWeeklyRequestCount(1);
        user.setLastRequestTime(LocalDateTime.now().minusDays(1));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://api.spoonacular.com/recipes/complexSearch")
                .queryParam("apiKey", "da3a95ad9e794dc3b5d43cf1f0f8cf60")
                .queryParam("query", "chicken")
                .queryParam("number", 21);


        String mockedJsonResponse = "{\"results\":[{\"id\":635675,\"title\":\"Boozy Bbq Chicken\",\"image\":\"https://img.spoonacular.com/recipes/635675-312x231.jpg\",\"imageType\":\"jpg\"},{\"id\":641836,\"title\":\"Easy Baked Parmesan Chicken\",\"image\":\"https://img.spoonacular.com/recipes/641836-312x231.jpg\",\"imageType\":\"jpg\"}]}";

        mockServer.expect(requestTo(builder.toUriString()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockedJsonResponse, MediaType.APPLICATION_JSON));


        ResponseEntity<String> response = advancedRecipeService.searchFoodAdvanced(options, 1);


        assertNotNull(response);


        JsonNode expectedJson = objectMapper.readTree(mockedJsonResponse);
        JsonNode actualJson = objectMapper.readTree(response.getBody());

        assertEquals(expectedJson.get("results"), actualJson.get("results"));
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testSearchFoodAdvanced_WithInvalidUser() {

        SearchFoodOptions options = new SearchFoodOptions();
        when(userRepository.findById(1)).thenReturn(Optional.empty());


        try {
            advancedRecipeService.searchFoodAdvanced(options, 1);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    @Disabled
    void testGetRecipeNutrition() throws Exception {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://api.spoonacular.com/recipes/{id}/nutritionWidget.json")
                .queryParam("apiKey", "da3a95ad9e794dc3b5d43cf1f0f8cf60");

        String apiUrl = builder.buildAndExpand(123).toUriString();

        mockServer.expect(requestTo(apiUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"calories\":\"68\",\"carbs\":\"5g\",\"fat\":\"4g\",\"protein\":\"2g\",\"bad\":[{\"amount\":\"68\",\"indented\":false,\"title\":\"Calories\",\"percentOfDailyNeeds\":3.41},{\"amount\":\"4g\",\"indented\":false,\"title\":\"Fat\",\"percentOfDailyNeeds\":6.75},{\"amount\":\"0.78g\",\"indented\":true", MediaType.APPLICATION_JSON));


        ResponseEntity<String> response = advancedRecipeService.getRecipeNutrition(123);


        assertNotNull(response);
        assertEquals("Mocked Nutrition Response", response.getBody());
    }
}
