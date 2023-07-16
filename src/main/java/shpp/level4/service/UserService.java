package shpp.level4.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.entity.User;
import shpp.level4.exception.UserNotFoundException;
import shpp.level4.constants.Gender;
import shpp.level4.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger("console");
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRequestDTO userRequest) {
        User.UserBuilder userBuilder = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(Gender.fromCode(userRequest.getGender()))
                .dateOfBirth(userRequest.getDateOfBirth());
        String taxId = userRequest.getTaxId();
        if(taxId != null) {
            userBuilder.taxId(taxId);
        }
        User user = userBuilder.build();
        logger.debug("User to create = {}", user);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user by ID: " + id));
    }

    public User updateUser(Long id, UserRequestDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updateUserFields(user, updateUserDTO);
            return userRepository.save(user);
        } else {
            throw new UserNotFoundException("No user by ID: " + id);
        }
    }

    private void updateUserFields(User user, UserRequestDTO updatedUser) {
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setTaxId(updatedUser.getTaxId());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setGender(Gender.fromCode(updatedUser.getGender()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
