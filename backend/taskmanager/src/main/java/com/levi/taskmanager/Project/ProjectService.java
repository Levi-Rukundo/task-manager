package com.levi.taskmanager.project;

import com.levi.taskmanager.user.User;
import com.levi.taskmanager.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(String ownerEmail, String name) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Project p = new Project();
        p.setName(name);
        p.setOwner(owner);

        return projectRepository.save(p);
    }

    public List<Project> getMyProjects(String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return projectRepository.findByOwner(owner);
    }

    public Project getMyProjectById(String ownerEmail, Long projectId) {
        Project p = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        if (!p.getOwner().getEmail().equals(ownerEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your project");
        }
        return p;
    }
    public Project renameProject(String ownerEmail, Long projectId, String newName) {
        Project p = getMyProjectById(ownerEmail, projectId); // already checks ownership
        p.setName(newName.trim());
        return projectRepository.save(p);
    }

    public void deleteProject(String ownerEmail, Long projectId) {
        Project p = getMyProjectById(ownerEmail, projectId); // ownership check
        projectRepository.delete(p);
    }

}
