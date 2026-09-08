package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.UserResponseDTO;
import com.taskhub.taskhub.dto.auth.project.ProjectResponseDTO;
import com.taskhub.taskhub.dto.auth.task.TaskResponseDTO;
import com.taskhub.taskhub.entity.Project;
import com.taskhub.taskhub.entity.Task;
import com.taskhub.taskhub.repository.ProjectRepository;
import com.taskhub.taskhub.repository.TaskRepository;
import com.taskhub.taskhub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public AdminService(UserRepository userRepository, ProjectRepository projectRepository,
                        TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getName(), u.getEmail()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> findAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::toProjectResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::toTaskResponseDTO)
                .toList();
    }

    private ProjectResponseDTO toProjectResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getOwner()
        );
    }

    private TaskResponseDTO toTaskResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getProject().getId()
        );
    }
}