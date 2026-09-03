package com.taskhub.taskhub.dto.auth.project;

import com.taskhub.taskhub.entity.User;
import jakarta.validation.constraints.NotBlank;

public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ProjectResponseDTO(Long id, String description, String name, User owner) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.owner = owner;
    }
}
