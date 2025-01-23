package com.saeid.service;

import com.saeid.Entity.Client;
import com.saeid.Entity.Role;
import com.saeid.Entity.User;
import com.saeid.model.UserDto;
import com.saeid.repository.ClientRepository;
import com.saeid.repository.RoleRepository;
import com.saeid.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final String DefaultClientName = "web";
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, ClientRepository clientRepository, RoleService roleService, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    public UserDto createUser(UserDto userDto) {

        if(userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        Client client = clientRepository.findByName(DefaultClientName).orElseThrow(() -> new IllegalArgumentException("Client not found"));

        User user = new User();
        if(userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            user.setRoles(Set.of(roleService.getDefaluRole()));
        } else {
            Set<Role> roles = userDto.getRoles()
                    .stream()
                    .map(roleDto -> roleRepository.findByName(roleDto.getName())
                            .orElseThrow(() -> new IllegalArgumentException("Role not found")))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setClient(client);
        User createdUser = userRepository.save(user);
        return createdUser.toDto();
    }

    public User verifyUser(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SecurityException("user not found"));
        boolean verified = passwordEncoder.matches(password, user.getPassword());
        if(verified) {
            return user;
        }else {
            throw new SecurityException("Invalid password");
        }
    }
}
