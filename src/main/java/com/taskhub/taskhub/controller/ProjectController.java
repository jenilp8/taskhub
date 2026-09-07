package com.taskhub.taskhub.controller;

import com.taskhub.taskhub.dto.auth.project.ProjectRequestDTO;
import com.taskhub.taskhub.dto.auth.project.ProjectResponseDTO;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.exception.UserNotFoundException;
import com.taskhub.taskhub.repository.UserRepository;
import com.taskhub.taskhub.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    public ProjectController(ProjectService projectService, UserRepository userRepository) {
        this.projectService = projectService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @Valid @RequestBody ProjectRequestDTO dto,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        ProjectResponseDTO project = projectService.createProject(dto, currentUser);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectResponseDTO>> getOwnProjects(
            Pageable pageable,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        Page<ProjectResponseDTO> projects = projectService.listOwnProjects(currentUser.getId(), pageable);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        return ResponseEntity.ok(projectService.getProjectById(id, currentUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequestDTO dto,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        return ResponseEntity.ok(projectService.updateProject(id, dto, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        projectService.deleteProjectById(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    private User resolveUser(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}