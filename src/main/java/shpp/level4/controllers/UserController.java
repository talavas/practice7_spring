package shpp.level4.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.entities.User;
import shpp.level4.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole(T(shpp.level4.constants.Roles).ADMIN.name())")
public class UserController {
    Logger logger = LoggerFactory.getLogger("console");
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserRequestDTO> createUser(@RequestBody @Valid UserRequestDTO userRequest) {
        logger.debug("User create request = {}", userRequest);
        User user = userService.findByUserName(userRequest.getUsername());
        if(user != null){
            throw new NoSuchElementException("Username not unique.");
        }
        return new ResponseEntity<>
                (userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public List<UserRequestDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserRequestDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserRequestDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestDTO updateUserDTO) {
        UserRequestDTO updatedUser = userService.updateUser(id, updateUserDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
