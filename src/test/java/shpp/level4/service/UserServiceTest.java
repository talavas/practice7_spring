package shpp.level4.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import shpp.level4.constants.Gender;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.entity.User;
import shpp.level4.exception.UserNotFoundException;
import shpp.level4.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO userRequest;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRequest = UserRequestDTO.builder()
                .firstName("Oleksandr")
                .lastName("Talavas")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .gender("MALE")
                .build();

        expectedUser = User.builder()
                .id(1L)
                .firstName("Oleksandr")
                .lastName("Talavas")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .gender(Gender.MALE)
                .build();
    }

    @Test
    void createUser_ValidUserRequest_ReturnsCreatedUser() {
       Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedUser);

        User createdUser = userService.createUser(userRequest);

        Assertions.assertEquals(expectedUser, createdUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        List<User> expectedUsers = new ArrayList<>();
        User user = User.builder()
                .id(1L)
                .firstName("Olena")
                .lastName("Kovalenko")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender(Gender.FEMALE)
                .build();
        expectedUsers.add(expectedUser);
        expectedUsers.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);


        List<User> users = userService.getAllUsers();

        Assertions.assertEquals(expectedUsers, users);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getUserById_NonExistingUserId_ThrowsException() {
        Long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    void updateUser_ExistingUserId_ReturnsUpdatedUser() {
        Long userId = 1L;
        UserRequestDTO updateUserDTO = UserRequestDTO.builder()
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .dateOfBirth(LocalDate.of(1995, 3, 15))
                .gender("MALE")
                .build();

        User expectedUpdatedUser = User.builder()
                .id(userId)
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .dateOfBirth(LocalDate.of(1995, 3, 15))
                .gender(Gender.MALE)
                .build();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedUpdatedUser);

        User updatedUser = userService.updateUser(userId, updateUserDTO);

        Assertions.assertEquals(expectedUpdatedUser, updatedUser);
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

}