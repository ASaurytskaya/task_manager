package by.it_academy.task.controller;

import by.it_academy.task.core.dto.ProjectCreate;
import by.it_academy.task.core.dto.ProjectView;
import by.it_academy.task.dao.entity.ProjectEntity;
import by.it_academy.task.service.api.IProjectService;
import by.it_academy.task.util.TPage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController(value = "api/v1/project")
public class ProjectController {
    private final IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        TPage<ProjectView> viewPage = projectService.getPage(page, size);
        return new ResponseEntity<>(viewPage, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public ResponseEntity<?> getProject(@PathVariable UUID uuid) {
        ProjectView project = projectService.getProject(uuid);
        return new ResponseEntity<>(project, HttpStatus.valueOf(200));
    }

    @PostMapping
    @ResponseStatus
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectCreate projectCreate) {
        projectService.save(projectCreate);
        return new ResponseEntity<>(HttpStatus.valueOf( 201));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectCreate projectCreate, @PathVariable UUID uuid, @PathVariable(name = "dt_update") LocalDateTime dtUpdate) {
        projectService.update(projectCreate, uuid, dtUpdate);
        return new ResponseEntity<>(HttpStatus.valueOf( 200));
    }
}

