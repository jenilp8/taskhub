package com.taskhub.taskhub.dto.auth.task;

import com.taskhub.taskhub.entity.Task;

import java.time.*;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Task.Status status;
    private Task.Priority priority;
    private LocalDate dueDate;
    private Long projectId;

    public TaskResponseDTO(Long id, String title, String description, Task.Status status,
                           Task.Priority priority, LocalDate dueDate, Long projectId) {
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
    public Task.Status getStatus() { return status; }
    public Task.Priority getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public Long getProjectId() { return projectId; }
}