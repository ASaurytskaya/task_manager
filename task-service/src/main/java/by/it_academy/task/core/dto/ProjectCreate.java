package by.it_academy.task.core.dto;

import by.it_academy.task.core.ProjectStatus;
import by.it_academy.task.core.UserRef;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProjectCreate {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private UserRef manager;

    @NotNull
    private List<UserRef> staff;

    @NotNull
    private ProjectStatus status;

    public ProjectCreate(String name, String description, UserRef manager, List<UserRef> staff, ProjectStatus status) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public ProjectCreate setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProjectCreate setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserRef getManager() {
        return manager;
    }

    public ProjectCreate setManager(UserRef manager) {
        this.manager = manager;
        return this;
    }

    public List<UserRef> getStaff() {
        return staff;
    }

    public ProjectCreate setStaff(List<UserRef> staff) {
        this.staff = staff;
        return this;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public ProjectCreate setStatus(ProjectStatus status) {
        this.status = status;
        return this;
    }
}
