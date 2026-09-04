package com.taskhub.taskhub.dto.auth.task;

import com.taskhub.taskhub.enums.TaskPriority;
import com.taskhub.taskhub.enums.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private Long projectId;

    public TaskResponseDTO(Long id, String title, String description, TaskStatus status,
                           TaskPriority priority, LocalDateTime dueDate, Long projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.projectId = projectId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public TaskPriority getPriority() { return priority; }
    public LocalDateTime getDueDate() { return dueDate; }
    public Long getProjectId() { return projectId; }
}