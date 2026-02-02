package com.levi.taskmanager.project;

import jakarta.validation.constraints.NotBlank;

public class UpdateProjectRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
