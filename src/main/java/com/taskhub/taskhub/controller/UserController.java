package com.taskhub.taskhub.controller;


import com.taskhub.taskhub.dto.auth.UserRequestDTO;
import com.taskhub.taskhub.dto.auth.UserResponseDTO;
import com.taskhub.taskhub.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/")
@Validated
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    };

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    };

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = userService.loginUser(userRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

}
