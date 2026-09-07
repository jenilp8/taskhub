package com.taskhub.taskhub.controller;

import com.taskhub.taskhub.dto.auth.task.TaskRequestDTO;
import com.taskhub.taskhub.dto.auth.task.TaskResponseDTO;
import com.taskhub.taskhub.entity.Task;
import com.taskhub.taskhub.entity.User;
import com.taskhub.taskhub.exception.ResourceNotFoundException;
import com.taskhub.taskhub.repository.UserRepository;
import com.taskhub.taskhub.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    // ---- /api/projects/{projectId}/tasks ----

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequestDTO dto,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        TaskResponseDTO task = taskService.createTask(projectId, dto, currentUser);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Page<TaskResponseDTO>> listTasks(
            @PathVariable Long projectId,
            @RequestParam(required = false) Task.Status status,
            @RequestParam(required = false) Task.Priority priority,
            Pageable pageable,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        Page<TaskResponseDTO> tasks = taskService.listTasksByProject(projectId, status, priority, currentUser, pageable);
        return ResponseEntity.ok(tasks);
    }

    // ---- /api/tasks/{taskId} ----

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> getById(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        return ResponseEntity.ok(taskService.getTaskById(taskId, currentUser));
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> update(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDTO dto,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        return ResponseEntity.ok(taskService.updateTask(taskId, dto, currentUser));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserDetails principal) {
        User currentUser = resolveUser(principal);
        taskService.deleteTask(taskId, currentUser);
        return ResponseEntity.noContent().build();
    }

    private User resolveUser(UserDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}