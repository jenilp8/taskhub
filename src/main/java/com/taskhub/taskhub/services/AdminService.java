package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.UserRequestDTO;
import com.taskhub.taskhub.dto.auth.UserResponseDTO;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.exception.UserNotFoundException;
import com.taskhub.taskhub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.taskhub.taskhub.enums.Role.ADMIN;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (!userRepository.existsByEmail(userRequestDTO.getEmail())) {
            User admin = new User();
            admin.setName(userRequestDTO.getName());
            admin.setEmail(userRequestDTO.getEmail());
            admin.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            admin.setRole(ADMIN);
            User createdAdmin = userRepository.save(admin);
            return toResponseDTO(createdAdmin);
        } else {
            throw new RuntimeException("Email already in use");
        }
    }

    public UserResponseDTO loginUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(userRequestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(userRequestDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return toResponseDTO(user);
    }

    public List<UserResponseDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::toResponseDTO).toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Helper Method
    private UserResponseDTO toResponseDTO(User user) {
        // building and returning UserResponseDTO from a User entity
        return new UserResponseDTO(user.getId(),user.getName(), user.getEmail());
    }

}