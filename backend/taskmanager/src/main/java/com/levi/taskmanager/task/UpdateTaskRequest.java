package com.levi.taskmanager.task;

public class UpdateTaskRequest {

    private String title;
    private TaskStatus status;

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
