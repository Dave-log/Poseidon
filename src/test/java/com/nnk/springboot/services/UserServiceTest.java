package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUserById_ExistingUser() {
        // Arrange
        int id = 1;
        User expectedUser = new User();
        expectedUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUser(id);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testGetUserById_NonExistingUser() {
        // Arrange
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testGetUserByUsername_ExistingUser() {
        // Arrange
        String username = "john";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUser(username);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUserByUsername_NonExistingUser() {
        // Arrange
        String username = "john";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUser(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testGetUsers() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        Iterable<User> actualUsers = userService.getUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User user = new User();

        // Act
        userService.delete(user);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }
}
