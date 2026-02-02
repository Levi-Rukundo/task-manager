package com.levi.taskmanager.task;

import com.levi.taskmanager.project.Project;
import com.levi.taskmanager.project.ProjectRepository;
import com.levi.taskmanager.user.User;
import com.levi.taskmanager.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(String ownerEmail, Long projectId, String title) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        // Ensure this project belongs to the user
        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your project");
        }

        Task t = new Task();
        t.setTitle(title);
        t.setOwner(owner);
        t.setProject(project);
        t.setStatus(TaskStatus.TODO);

        return taskRepository.save(t);
    }

    public List<Task> listTasks(String ownerEmail, Long projectId, TaskStatus status, String q) {
            User owner = userRepository.findByEmail(ownerEmail)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

            Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your project");
        }

        String query = (q == null) ? null : q.trim();
        boolean hasQ = (query != null && !query.isEmpty());
        boolean hasStatus = (status != null);

        if (hasStatus && hasQ) {
            return taskRepository.findByOwnerAndProjectAndStatusAndTitleContainingIgnoreCase(owner, project, status, query);
        }
        if (hasStatus) {
            return taskRepository.findByOwnerAndProjectAndStatus(owner, project, status);
        }
        if (hasQ) {
            return taskRepository.findByOwnerAndProjectAndTitleContainingIgnoreCase(owner, project, query);
        }
        return taskRepository.findByOwnerAndProject(owner, project);
    }
    public Task updateStatus(String ownerEmail, Long taskId, TaskStatus newStatus) {
    Task t = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

    // ownership check
    if (!t.getOwner().getEmail().equals(ownerEmail)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your task");
    }

    t.setStatus(newStatus);
    return taskRepository.save(t);
    }
    public Task updateTask(String ownerEmail, Long taskId, String newTitle, TaskStatus newStatus) {
    Task t = taskRepository.findById(taskId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

    if (!t.getOwner().getEmail().equals(ownerEmail)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your task");
    }

    if (newTitle != null) {
        String trimmed = newTitle.trim();
        if (trimmed.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be blank");
        }
        t.setTitle(trimmed);
    }

    if (newStatus != null) {
        t.setStatus(newStatus);
    }

    return taskRepository.save(t);
}

public void deleteTask(String ownerEmail, Long taskId) {
    Task t = taskRepository.findById(taskId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        if (!t.getOwner().getEmail().equals(ownerEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your task");
        }

        taskRepository.delete(t);
    }


}
