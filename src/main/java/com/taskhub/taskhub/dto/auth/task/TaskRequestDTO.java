package com.taskhub.taskhub.dto.auth.task;

import com.taskhub.taskhub.entity.Task;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.*;

public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private Task.Status status; // optional on create — service defaults to TODO

    private Task.Priority priority;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Task.Status getStatus() { return status; }
    public void setStatus(Task.Status status) { this.status = status; }

    public Task.Priority getPriority() { return priority; }
    public void setPriority(Task.Priority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate  dueDate) { this.dueDate = dueDate; }
}