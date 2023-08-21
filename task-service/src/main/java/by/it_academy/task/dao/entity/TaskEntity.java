package by.it_academy.task.dao.entity;

import by.it_academy.task.core.ProjectRef;
import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.dao.converter.ProjectRefConverter;
import by.it_academy.task.dao.converter.UserRefConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
    @Converts({
            @Convert(attributeName = "project", converter = ProjectRefConverter.class),
            @Convert(attributeName = "implementer", converter = UserRefConverter.class)
    })
public class TaskEntity {

    @Id
    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "dt_create", nullable = false)
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update", nullable = false)
    private LocalDateTime dtUpdate;

    @Column(name = "project", nullable = false)
    private ProjectRef project;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "implementer", nullable = false)
    private UserRef implementer;

    public TaskEntity() {
    }

    public TaskEntity(UUID taskId, LocalDateTime dtCreate, LocalDateTime dtUpdate, ProjectRef project,
                      String title, String description, TaskStatus status, UserRef implementer) {
        this.taskId = taskId;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public ProjectRef getProject() {
        return project;
    }

    public void setProject(ProjectRef project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public UserRef getImplementer() {
        return implementer;
    }

    public void setImplementer(UserRef implementer) {
        this.implementer = implementer;
    }
}
