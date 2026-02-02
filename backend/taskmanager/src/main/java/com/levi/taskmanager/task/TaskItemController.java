package com.levi.taskmanager.task;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskItemController {

    private final TaskService taskService;

    public TaskItemController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateTaskStatusRequest req,
            Authentication auth
    ) {
        String email = auth.getName();
        Task t = taskService.updateStatus(email, taskId, req.getStatus());
        return new TaskResponse(t.getId(), t.getTitle(), t.getStatus());
    }
    @PatchMapping("/{taskId}")
    public TaskResponse updateTask(
        @PathVariable Long taskId,
        @RequestBody UpdateTaskRequest req,
        Authentication auth
        ) {
        String email = auth.getName();
        Task t = taskService.updateTask(email, taskId, req.getTitle(), req.getStatus());
        return new TaskResponse(t.getId(), t.getTitle(), t.getStatus());
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId, Authentication auth) {
        String email = auth.getName();
        taskService.deleteTask(email, taskId);
    }

}
