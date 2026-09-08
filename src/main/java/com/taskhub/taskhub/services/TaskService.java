package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.task.TaskRequestDTO;
import com.taskhub.taskhub.dto.auth.task.TaskResponseDTO;
import com.taskhub.taskhub.entity.Project;
import com.taskhub.taskhub.entity.Task;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.enums.Role;
import com.taskhub.taskhub.exception.ResourceNotFoundException;
import com.taskhub.taskhub.exception.AccessDeniedException;
import com.taskhub.taskhub.repository.ProjectRepository;
import com.taskhub.taskhub.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public TaskResponseDTO createTask(Long projectId, TaskRequestDTO dto, User currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        assertOwnerOrAdmin(project, currentUser);

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : Task.Status.TODO);        
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setProject(project);

        taskRepository.save(task);
        return toResponseDTO(task);
    }

    @Transactional
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO dto, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        assertOwnerOrAdmin(task.getProject(), currentUser);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());

        return toResponseDTO(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        assertOwnerOrAdmin(task.getProject(), currentUser);

        return toResponseDTO(task);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponseDTO> listTasksByProject(Long projectId, Task.Status status, Task.Priority priority,
                                                    User currentUser, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        assertOwnerOrAdmin(project, currentUser);

        Page<Task> tasks;
        if (status != null && priority != null) {
            tasks = taskRepository.findByProjectIdAndStatusAndPriority(projectId, status, priority, pageable);
        } else if (status != null) {
            tasks = taskRepository.findByProjectIdAndStatus(projectId, status, pageable);
        } else if (priority != null) {
            tasks = taskRepository.findByProjectIdAndPriority(projectId, priority, pageable);
        } else {
            tasks = taskRepository.findByProjectId(projectId, pageable);
        }

        return tasks.map(this::toResponseDTO);
    }

    @Transactional
    public void deleteTask(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        assertOwnerOrAdmin(task.getProject(), currentUser);

        taskRepository.delete(task);
    }

    private void assertOwnerOrAdmin(Project project, User currentUser) {
        boolean isOwner = project.getOwner().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You do not have permission to access this task");
        }
    }

    private TaskResponseDTO toResponseDTO(Task task) {
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