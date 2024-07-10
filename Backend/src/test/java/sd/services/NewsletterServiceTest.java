package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import sd.entities.Subscriber;
import sd.repositories.SubscriberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class NewsletterServiceTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private NewsletterService newsletterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testS_SubscribedSuccessfully() {
        String email = "test@example.com";
        when(subscriberRepository.findByEmail(email)).thenReturn(Optional.empty());

        String result = newsletterService.s(email);

        assertEquals("Subscribed successfully", result);
        verify(subscriberRepository, times(1)).save(any());
    }

    @Test
    void testS_AlreadySubscribed() {
        String email = "test@example.com";
        when(subscriberRepository.findByEmail(email)).thenReturn(Optional.of(new Subscriber()));

        String result = newsletterService.s(email);

        assertEquals("Already subscribed", result);
        verify(subscriberRepository, never()).save(any());
    }

}

