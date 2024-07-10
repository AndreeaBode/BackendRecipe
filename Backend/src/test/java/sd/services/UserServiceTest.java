package sd.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sd.dtos.LoginDTO;
import sd.dtos.RegisterDTO;
import sd.dtos.UserDTO;
import sd.entities.User;
import sd.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRegister_UserExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");
        registerDTO.setConfirmedPassword("password");
        registerDTO.setRole("user");

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setName("Test User");
        existingUser.setEmail("test@example.com");
        existingUser.setRole("user");
        when(userRepository.findByEmail(anyString())).thenReturn(existingUser);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register(registerDTO));
        assertEquals("A user with this email already exists.", exception.getMessage());
    }

    @Test
    void testLogin_Success() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setName("Test User");
        existingUser.setEmail("test@example.com");
        existingUser.setPassword(userService.hashPassword("password"));
        existingUser.setRole("user");
        when(userRepository.findByEmail(anyString())).thenReturn(existingUser);

        User result = userService.login(loginDTO);

        assertNotNull(result);
        assertEquals(existingUser.getId(), result.getId());
        assertEquals(existingUser.getEmail(), result.getEmail());
    }

    @Test
    void testLogin_InvalidCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("invalid_hashed_password");
        when(userRepository.findByEmail(anyString())).thenReturn(existingUser);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login(loginDTO));
        assertEquals("Authentication failed. Invalid email or password.", exception.getMessage());
    }

}
