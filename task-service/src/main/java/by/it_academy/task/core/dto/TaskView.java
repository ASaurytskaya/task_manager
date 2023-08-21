package by.it_academy.task.core.dto;

import by.it_academy.task.core.ProjectRef;
import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.UserRef;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskView {

    private final UUID taskId;

    private final LocalDateTime dtCreate;

    private final LocalDateTime dtUpdate;

    private final ProjectRef project;

    private final String title;

    private final String description;

    private final TaskStatus status;

    private final UserRef implementer;

    private TaskView(TaskViewBuilder builder) {
        this.taskId = builder.taskId;
        this.dtCreate = builder.dtCreate;
        this.dtUpdate = builder.dtUpdate;
        this.project = builder.project;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;
        this.implementer = builder.implementer;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public ProjectRef getProject() {
        return project;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public UserRef getImplementer() {
        return implementer;
    }

    public static class TaskViewBuilder {
        private UUID taskId;

        private LocalDateTime dtCreate;

        private LocalDateTime dtUpdate;

        private ProjectRef project;

        private String title;

        private String description;

        private TaskStatus status;

        private UserRef implementer;

        public TaskViewBuilder() {
        }

        public TaskViewBuilder setTaskId(UUID taskId) {
            this.taskId = taskId;
            return this;
        }

        public TaskViewBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public TaskViewBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public TaskViewBuilder setProject(ProjectRef project) {
            this.project = project;
            return this;
        }

        public TaskViewBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public TaskViewBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskViewBuilder setStatus(TaskStatus status) {
            this.status = status;
            return this;
        }

        public TaskViewBuilder setImplementer(UserRef implementer) {
            this.implementer = implementer;
            return this;
        }

        public TaskView build() {
            return new TaskView(this);
        }
    }
}
