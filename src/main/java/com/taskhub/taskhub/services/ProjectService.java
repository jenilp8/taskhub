package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.project.ProjectRequestDTO;
import com.taskhub.taskhub.dto.auth.project.ProjectResponseDTO;
import com.taskhub.taskhub.entity.Project;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.enums.Role;
import com.taskhub.taskhub.exception.ResourceNotFoundException;
import com.taskhub.taskhub.repository.ProjectRepository;
import com.taskhub.taskhub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO dto, User currentUser) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setOwner(currentUser);
        projectRepository.save(project);
        return toResponseDTO(project);
    }

    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO dto, User currentUser) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        assertOwnerOrAdmin(project, currentUser);

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        // owner is never reassigned via update

        return toResponseDTO(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(Long id, User currentUser) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        assertOwnerOrAdmin(project, currentUser);

        return toResponseDTO(project);
    }

    @Transactional(readOnly = true)
    public Page<ProjectResponseDTO> listOwnProjects(Long ownerId, Pageable pageable) {
        return projectRepository.findByOwnerId(ownerId, pageable)
                .map(this::toResponseDTO);
    }

    @Transactional
    public void deleteProjectById(Long id, User currentUser) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        assertOwnerOrAdmin(project, currentUser);

        projectRepository.delete(project);
    }

    private void assertOwnerOrAdmin(Project project, User currentUser) {
        boolean isOwner = project.getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You do not have permission to access this project");
        }
    }

    private ProjectResponseDTO toResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner()
        );
    }
}