package shpp.level4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shpp.level4.constants.Gender;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.entity.User;
import shpp.level4.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender("M")
                .build();

        User createdUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .build();

        given(service.createUser(any(UserRequestDTO.class))).willReturn(createdUser);

        mvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdUser.getId()))
                .andExpect(jsonPath("$.firstName").value(createdUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(createdUser.getLastName()))
                .andExpect(jsonPath("$.dateOfBirth").value(createdUser.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.gender").value(Gender.MALE.toString()));
    }

    @Test
    void getUserById() throws Exception {
        User user = User.builder()
                .id(1L)
                .firstName("Alex")
                .lastName("Talavas")
                .dateOfBirth(LocalDate.of(1980, 4, 11))
                .build();
        given(service.getUserById(1L)).willReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.dateOfBirth").value(user.getDateOfBirth().toString()));
    }

    @Test
    void getAllUsers() throws Exception {
        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1992, 5, 20))
                .build();

        List<User> users = Arrays.asList(user1, user2);

        given(service.getAllUsers()).willReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].firstName").value(user1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(user1.getLastName()))
                .andExpect(jsonPath("$[0].dateOfBirth").value(user1.getDateOfBirth().toString()))
                .andExpect(jsonPath("$[1].id").value(user2.getId()))
                .andExpect(jsonPath("$[1].firstName").value(user2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(user2.getLastName()))
                .andExpect(jsonPath("$[1].dateOfBirth").value(user2.getDateOfBirth().toString()));
    }

    @Test
    void updateUser() throws Exception {
        UserRequestDTO updateUserDTO = UserRequestDTO.builder()
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .dateOfBirth(LocalDate.of(1995, 3, 15))
                .gender("M")
                .build();

        User updatedUser = User.builder()
                .id(1L)
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .dateOfBirth(LocalDate.of(1995, 3, 15))
                .gender(Gender.MALE)
                .build();

        given(service.updateUser(1L, updateUserDTO)).willReturn(updatedUser);

        mvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedUser.getId()))
                .andExpect(jsonPath("$.firstName").value(updatedUser.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedUser.getLastName()))
                .andExpect(jsonPath("$.dateOfBirth").value(updatedUser.getDateOfBirth().toString()));
    }

    @Test
    void updateUser_UserNotFound() throws Exception {
        UserRequestDTO updateUserDTO = UserRequestDTO.builder()
                .firstName("Updated First Name")
                .lastName("Updated Last Name")
                .dateOfBirth(LocalDate.of(1995, 3, 15))
                .gender("M")
                .build();

        given(service.updateUser(1L, updateUserDTO)).willReturn(null);

        mvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isNotFound());
    }
    @Test
    void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}