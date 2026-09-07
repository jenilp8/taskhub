package com.taskhub.taskhub.controller;

import com.taskhub.taskhub.dto.auth.UserResponseDTO;
import com.taskhub.taskhub.dto.auth.project.ProjectResponseDTO;
import com.taskhub.taskhub.dto.auth.task.TaskResponseDTO;
import com.taskhub.taskhub.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.findAllUsers());
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(adminService.findAllProjects());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(adminService.findAllTasks());
    }
}