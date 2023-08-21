package by.it_academy.task.core.dto;

import by.it_academy.task.core.ProjectStatus;
import by.it_academy.task.core.UserRef;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProjectView {

    private final UUID projectId;

    private final LocalDateTime dtCreate;

    private final LocalDateTime dtUpdate;

    private final String name;

    private final String description;

    private final UserRef manager;

    private final List<UserRef> stuff;

    private final ProjectStatus status;

    private ProjectView( ProjectViewBuilder builder) {
        this.projectId = builder.projectId;
        this.dtCreate = builder.dtCreate;
        this.dtUpdate = builder.dtUpdate;
        this.name = builder.name;
        this.description = builder.description;
        this.manager = builder.manager;
        this.stuff = builder.stuff;
        this.status = builder.status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UserRef getManager() {
        return manager;
    }

    public List<UserRef> getStuff() {
        return stuff;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public static class ProjectViewBuilder {
        private UUID projectId;

        private LocalDateTime dtCreate;

        private LocalDateTime dtUpdate;

        private String name;

        private String description;

        private UserRef manager;

        private List<UserRef> stuff;

        private ProjectStatus status;

        public ProjectViewBuilder() {
        }

        public ProjectViewBuilder setProjectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public ProjectViewBuilder setDtCreate(LocalDateTime dtCreate) {
            this.dtCreate = dtCreate;
            return this;
        }

        public ProjectViewBuilder setDtUpdate(LocalDateTime dtUpdate) {
            this.dtUpdate = dtUpdate;
            return this;
        }

        public ProjectViewBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProjectViewBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProjectViewBuilder setManager(UserRef manager) {
            this.manager = manager;
            return this;
        }

        public ProjectViewBuilder setStuff(List<UserRef> stuff) {
            this.stuff = stuff;
            return this;
        }

        public ProjectViewBuilder setStatus(ProjectStatus status) {
            this.status = status;
            return this;
        }

        public ProjectView build() {
            return new ProjectView(this);
        }
    }

}
