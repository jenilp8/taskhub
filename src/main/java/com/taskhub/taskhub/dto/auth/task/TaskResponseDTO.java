package com.taskhub.taskhub.dto.auth.task;

import java.time.LocalDateTime;

public class TaskResponseDTO {
    private  String title;
    private String description;
    private String owner;
    private String Status;
    private String priority;
    private LocalDateTime dueDate;
    private Long projectId;

    public TaskResponseDTO(String title, String description, String owner, String status, String priority, LocalDateTime dueDate, Long projectId) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        Status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
