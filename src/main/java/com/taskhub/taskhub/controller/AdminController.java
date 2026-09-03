package com.taskhub.taskhub.controller;

import com.taskhub.taskhub.dto.auth.UserRequestDTO;
import com.taskhub.taskhub.dto.auth.UserResponseDTO;
import com.taskhub.taskhub.services.AdminService;
import com.taskhub.taskhub.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Validated
public class AdminController {

    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    };

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = adminService.registerUser(userRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    };

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO user = adminService.loginUser(userRequestDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return new ResponseEntity<>(adminService.findAllUsers(), HttpStatus.OK);
    }

}
