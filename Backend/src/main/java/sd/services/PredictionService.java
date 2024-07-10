package sd.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    private final String apiUrl = "https://licenta-model-92ff946e2e32.herokuapp.com/predict"; // Sau URL-ul către serviciul tău Spring Boot

    private final RestTemplate restTemplate;

    @Autowired
    public PredictionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getPrediction(Object ingredients) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(ingredients, headers);

        System.out.println("Sending request to " + apiUrl + " with data: " + ingredients);

        ResponseEntity<Object> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Object.class);

        System.out.println("Received response: " + response);
        return response.getBody();
    }
}
