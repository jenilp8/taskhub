package com.taskhub.taskhub.controller;

import com.taskhub.taskhub.dto.auth.project.ProjectRequestDTO;
import com.taskhub.taskhub.dto.auth.project.ProjectResponseDTO;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO project = projectService.createProject(projectRequestDTO);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getOwnProjects(Pageable pageable, ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO projects = projectService.listOwnProjects(projectRequestDTO.getOwner().getId(), pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody ProjectRequestDTO dto,
                                                      User currentUser) {
        return ResponseEntity.ok(projectService.updateProject(id, dto, currentUser));
    }
}
