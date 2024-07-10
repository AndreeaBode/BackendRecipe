package sd.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sd.dtos.LoginDTO;
import sd.dtos.RegisterDTO;
import sd.dtos.UserDTO;
import sd.entities.User;
import sd.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");
        registerDTO.setConfirmedPassword("password");
        registerDTO.setRole("user");

        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole("user");

        when(userService.register(any(RegisterDTO.class))).thenReturn(user);

        ResponseEntity<?> response = userController.register(registerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLogin() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@example.com");
        loginDTO.setPassword("password");

        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setRole("user");

        when(userService.login(any(LoginDTO.class))).thenReturn(user);

        ResponseEntity<?> response = userController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(1, "Test User", "test@example.com", "user"));

        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users.size(), response.getBody().size());
    }

    @Test
    void testGetUserById() {
        int userId = 1;
        UserDTO userDTO = new UserDTO(userId, "Test User", "test@example.com", "user");

        when(userService.getUserById(anyInt())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateUser() {
        int userId = 1;
        UserDTO userDTO = new UserDTO(userId, "Updated User", "updated@example.com", "admin");

        when(userService.updateUser(anyInt(), any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO.getId(), response.getBody().getId());
        assertEquals(userDTO.getName(), response.getBody().getName());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
        assertEquals(userDTO.getRole(), response.getBody().getRole());
    }

    @Test
    void testDeleteUser() {
        int userId = 1;
        UserDTO userDTO = new UserDTO(userId, "Test User", "test@example.com", "user");

        when(userService.deleteUser(anyInt())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO.getId(), response.getBody().getId());
    }
}
