package com.levi.taskmanager.task;

import com.levi.taskmanager.project.Project;
import com.levi.taskmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerAndProject(User owner, Project project);
    List<Task> findByOwnerAndProjectAndStatus(User owner, Project project, TaskStatus status);
    List<Task> findByOwnerAndProjectAndTitleContainingIgnoreCase(User owner, Project project, String q);
    List<Task> findByOwnerAndProjectAndStatusAndTitleContainingIgnoreCase(User owner, Project project, TaskStatus status, String q);

}
