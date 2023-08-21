package by.it_academy.task.core;

import java.util.UUID;

public class ProjectRef {
    private UUID projectId;

    public ProjectRef(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public ProjectRef setProjectId(UUID projectId) {
        this.projectId = projectId;
        return this;
    }
}
