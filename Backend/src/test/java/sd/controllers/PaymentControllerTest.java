package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.entities.User;
import sd.repositories.UserRepository;
import sd.services.PaymentService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateCheckoutSession_Success() throws Exception {
        int userId = 1;
        String sessionUrl = "http://example.com/session";

        when(paymentService.createCheckoutSession(anyInt())).thenReturn(sessionUrl);
        ResponseEntity<Map<String, String>> response = paymentController.createCheckoutSession(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionUrl, response.getBody().get("url"));
    }
}
