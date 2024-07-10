package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sd.entities.User;
import sd.repositories.UserPremiumRepository;
import sd.repositories.UserRepository;
import sd.services.PaymentService;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPremiumRepository userPremiumRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testUpgradeToPremium_AlreadyPremium() {
        User user = new User();
        user.setId(1);
        user.setRole("Premium");


        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        String result = paymentService.upgradeToPremium(1);

        assertEquals("You are already a premium user.", result);
        assertEquals("Premium", user.getRole());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testUpgradeToPremium_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        String result = paymentService.upgradeToPremium(1);

        assertEquals("Oops! Something went wrong. Please try again.", result);
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }
}
