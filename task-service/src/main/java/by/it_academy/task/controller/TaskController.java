package by.it_academy.task.controller;

import by.it_academy.task.controller.converter.StringProjectRefConverter;
import by.it_academy.task.controller.converter.StringUserRefConverter;
import by.it_academy.task.core.ProjectRef;
import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.core.dto.TaskCreate;
import by.it_academy.task.core.dto.TaskView;
import by.it_academy.task.service.api.ITaskService;
import by.it_academy.task.util.TPage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/task")
public class TaskController {

    private final ITaskService taskService;
    private final StringProjectRefConverter stringProjectRefConverter;
    private final StringUserRefConverter stringUserRefConverter;

    public TaskController(ITaskService taskService, StringProjectRefConverter stringProjectRefConverter, StringUserRefConverter stringUserRefConverter) {
        this.taskService = taskService;
        this.stringProjectRefConverter = stringProjectRefConverter;
        this.stringUserRefConverter = stringUserRefConverter;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     @RequestParam(required = false) List<ProjectRef> project,
                                     @RequestParam(required = false) List<UserRef> implementer,
                                     @RequestParam(required = false) List<TaskStatus> status
    ) {

        TPage<TaskView> pageView = taskService.getPage(page, size, project, implementer, status);
        return new ResponseEntity<>(pageView, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public ResponseEntity<?> getTask(@PathVariable UUID uuid) {
        TaskView task = taskService.getTask(uuid);
        return new ResponseEntity<>(task, HttpStatus.valueOf(200));
    }

    @PostMapping
    @ResponseStatus
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskCreate taskCreate) {
        taskService.save(taskCreate);
        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskCreate taskCreate, @PathVariable UUID uuid, @PathVariable(name = "dt_update") LocalDateTime dtUpdate) {
        taskService.update(taskCreate, uuid, dtUpdate);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}/status/{status}")
    public ResponseEntity<?> updateTask(@PathVariable UUID uuid, @PathVariable(name = "dt_update") LocalDateTime dtUpdate, @PathVariable(name = "status") String status) {
        taskService.updateStatus(uuid, dtUpdate, status);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}