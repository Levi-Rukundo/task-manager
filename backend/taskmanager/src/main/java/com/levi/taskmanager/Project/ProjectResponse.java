package com.levi.taskmanager.project;

public class ProjectResponse {
    private Long id;
    private String name;

    public ProjectResponse() {}

    public ProjectResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
