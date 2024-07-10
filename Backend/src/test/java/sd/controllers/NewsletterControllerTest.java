package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.services.NewsletterService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsletterControllerTest {

    @Mock
    private NewsletterService newsletterService;

    @InjectMocks
    private NewsletterController newsletterController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void handleSubscribeRequest_Success() {
        String email = "test@example.com";
        String expectedMessage = "Subscribed successfully";

        when(newsletterService.s(email)).thenReturn(expectedMessage);

        ResponseEntity<String> response = newsletterController.handleSubscribeRequest(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(newsletterService, times(1)).s(email);
    }

    @Test
    void handleSubscribeRequest_AlreadySubscribed() {
        String email = "test@example.com";
        String expectedMessage = "Already subscribed";

        when(newsletterService.s(email)).thenReturn(expectedMessage);

        ResponseEntity<String> response = newsletterController.handleSubscribeRequest(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(newsletterService, times(1)).s(email);
    }

}

