package shpp.level4.utils;

import org.springframework.stereotype.Service;
import shpp.level4.constants.Gender;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.entities.User;

@Service
public class MappingUtils {

    public static UserRequestDTO mapToUserRequestDTO(User user){
        return UserRequestDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .taxId(user.getTaxId())
                .gender(user.getGender().name())
                .build();
    }

    public static User mapToUser(UserRequestDTO userRequestDTO){
        return User.builder()
                .id(userRequestDTO.getId())
                .username(userRequestDTO.getUsername())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .dateOfBirth(userRequestDTO.getDateOfBirth())
                .taxId(userRequestDTO.getTaxId())
                .gender(Gender.fromCode(userRequestDTO.getGender()))
                .build();
    }
}
