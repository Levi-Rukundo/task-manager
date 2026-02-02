package com.levi.taskmanager.task;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse create(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequest req,
            Authentication auth
    ) {
        String email = auth.getName();
        Task t = taskService.createTask(email, projectId, req.getTitle());
        return new TaskResponse(t.getId(), t.getTitle(), t.getStatus());
    }

    @GetMapping
public List<TaskResponse> list(
    @PathVariable Long projectId,
    @RequestParam(required = false) TaskStatus status,
    @RequestParam(required = false) String q,
    Authentication auth) {
    String email = auth.getName();
    return taskService.listTasks(email, projectId, status, q).stream()
        .map(t -> new TaskResponse(t.getId(), t.getTitle(), t.getStatus()))
        .toList();
    }

}
