package com.levi.taskmanager.task;

public class TaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;

    public TaskResponse() {}

    public TaskResponse(Long id, String title, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
