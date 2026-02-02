package com.levi.taskmanager.project;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectResponse create(@Valid @RequestBody CreateProjectRequest req, Authentication auth) {
    String email = auth.getName();
    Project p = projectService.createProject(email, req.getName());
    return new ProjectResponse(p.getId(), p.getName());
    }

    @GetMapping
    public List<ProjectResponse> mine(Authentication auth) {
    String email = auth.getName();
    return projectService.getMyProjects(email).stream()
            .map(p -> new ProjectResponse(p.getId(), p.getName()))
            .toList();
    }

    @GetMapping("/{id}")
    public ProjectResponse getOne(@PathVariable Long id, Authentication auth) {
    String email = auth.getName();
    Project p = projectService.getMyProjectById(email, id);
    return new ProjectResponse(p.getId(), p.getName());
    }
    @PatchMapping("/{id}")
    public ProjectResponse rename(
        @PathVariable Long id,
        @Valid @RequestBody UpdateProjectRequest req,
        Authentication auth
        ) {
        String email = auth.getName();
        Project p = projectService.renameProject(email, id, req.getName());
        return new ProjectResponse(p.getId(), p.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        projectService.deleteProject(email, id);
    }


}
