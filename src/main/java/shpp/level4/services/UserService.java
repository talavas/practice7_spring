package shpp.level4.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shpp.level4.constants.Roles;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.entities.RoleEntity;
import shpp.level4.entities.User;
import shpp.level4.exceptions.UserNotFoundException;
import shpp.level4.constants.Gender;
import shpp.level4.repositories.RoleRepository;
import shpp.level4.repositories.UserRepository;
import shpp.level4.utils.MappingUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    public UserRequestDTO createUser(UserRequestDTO userRequest) {
        User user = MappingUtils.mapToUser(userRequest);
        Optional<RoleEntity> roleOptional = roleRepository.findById(Roles.USER.getRoleId());
        roleOptional.ifPresent(user::setRole);

        log.debug("Creating user = {}", user);

        return MappingUtils.mapToUserRequestDTO(userRepository.save(user));
    }

    public List<UserRequestDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(MappingUtils::mapToUserRequestDTO).toList();
    }

    public UserRequestDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return MappingUtils.mapToUserRequestDTO(user.get());
        }else{
            throw new UserNotFoundException("No user by ID: " + id);
        }

    }

    public UserRequestDTO updateUser(Long id, UserRequestDTO updateUserDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updateUserFields(user, updateUserDTO);
            return MappingUtils.mapToUserRequestDTO(userRepository.save(user));
        } else {
            throw new UserNotFoundException("No user by ID: " + id);
        }
    }

    private void updateUserFields(User user, UserRequestDTO updatedUser) {
        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setTaxId(updatedUser.getTaxId());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setGender(Gender.fromCode(updatedUser.getGender()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        log.debug("Found user = {}", user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), mapRolesToAuthorities(user));
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(User user){
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        if(!user.getPermissions().isEmpty()){
            authorities = user.getPermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName().toString()))
                    .collect(Collectors.toSet());
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName().name()));
        return authorities;
    }
}
