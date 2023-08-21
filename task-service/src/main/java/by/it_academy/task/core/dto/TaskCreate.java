package by.it_academy.task.core.dto;

import by.it_academy.task.core.ProjectRef;
import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.UserRef;
import jakarta.validation.constraints.NotNull;

public class TaskCreate {
    @NotNull
    private ProjectRef project;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private UserRef implementer;

    public TaskCreate() {
    }

    public ProjectRef getProject() {
        return project;
    }

    public TaskCreate setProject(ProjectRef project) {
        this.project = project;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TaskCreate setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskCreate setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskCreate setStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public UserRef getImplementer() {
        return implementer;
    }

    public TaskCreate setImplementer(UserRef implementer) {
        this.implementer = implementer;
        return this;
    }
}
