package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.project.ProjectRequestDTO;
import com.taskhub.taskhub.dto.auth.project.ProjectResponseDTO;
import com.taskhub.taskhub.entity.Project;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.exception.ResourceNotFoundException;
import com.taskhub.taskhub.repository.ProjectRepository;
import com.taskhub.taskhub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
        private final ProjectRepository projectRepository;
        private final UserRepository userRepository;
        public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
            this.projectRepository = projectRepository;
            this.userRepository = userRepository;
        }

        public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
            Project project = new Project();
            project.setName(projectRequestDTO.getName());
            project.setDescription(projectRequestDTO.getDescription());
            project.setOwner(projectRequestDTO.getOwner());
            projectRepository.save(project);
            return toResponseDTO(project);
        }

        public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO, User currentUser) {
            Project project = projectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            Optional<User> isAdmin = userRepository.findById(projectRequestDTO.getOwner().getId());
                project.setName(projectRequestDTO.getName());
                project.setDescription(projectRequestDTO.getDescription());
                project.setOwner(projectRequestDTO.getOwner());

                return toResponseDTO(projectRepository.save(project));
        }

//        public Project findProjectById(Long id) {
//               Project project = projectRepository.findById(id)
//                       .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
//               return project;
//        }

        public Page<ProjectResponseDTO> listOwnProjects(Long ownerId, Pageable pageable) {
            return projectRepository.findByOwnerId(ownerId, pageable)
                    .map(this::toResponseDTO);
        }

        public void deleteProjectById(Long id) {
            projectRepository.deleteById(id);
        }

        // Helper Method
        private ProjectResponseDTO toResponseDTO(Project project) {
            return new ProjectResponseDTO(
                    project.getId(),
                    project.getName(),
                    project.getDescription(),
                    project.getOwner()
            );
        }
}
