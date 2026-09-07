package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.AuthResponseDTO;
import com.taskhub.taskhub.dto.auth.LoginRequestDTO;
import com.taskhub.taskhub.dto.auth.UserRequestDTO;
import com.taskhub.taskhub.dto.auth.UserResponseDTO;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.exception.UserNotFoundException;
import com.taskhub.taskhub.repository.UserRepository;
import com.taskhub.taskhub.security.CustomUserDetailsService;
import com.taskhub.taskhub.security.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.taskhub.taskhub.enums.Role.USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new RuntimeException("Email already in use"); // fix to a proper exception separately
        }
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(USER);
        User createdUser = userRepository.save(user);
        return toResponseDTO(createdUser);
    }

    public AuthResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails, user.getId(), user.getRole().name());

        return new AuthResponseDTO(token);
    }

    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}