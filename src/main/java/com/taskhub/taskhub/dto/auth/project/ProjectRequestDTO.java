package com.taskhub.taskhub.dto.auth.project;

import com.taskhub.taskhub.entity.User;
import jakarta.validation.constraints.NotBlank;

public class ProjectRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
}
