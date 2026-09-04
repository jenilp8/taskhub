package com.taskhub.taskhub.services;

import com.taskhub.taskhub.dto.auth.task.TaskRequestDTO;
import com.taskhub.taskhub.dto.auth.task.TaskResponseDTO;
import com.taskhub.taskhub.entity.Project;
import com.taskhub.taskhub.entity.Task;
import com.taskhub.taskhub.enums.TaskStatus;
import com.taskhub.taskhub.enums.TaskPriority;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.exception.ResourceNotFoundException;
import com.taskhub.taskhub.repository.ProjectRepository;
import com.taskhub.taskhub.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponseDTO createTask(Long projectId, TaskRequestDTO dto, User currentUser) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setProject(project);

        taskRepository.save(task);
        return toResponseDTO(task);
    }

    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO dto, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());

        return toResponseDTO(taskRepository.save(task));
    }

    public TaskResponseDTO getTaskById(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return toResponseDTO(task);
    }

    public void deleteTask(Long taskId, User currentUser) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
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