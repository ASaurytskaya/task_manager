package by.it_academy.task.dao.entity;

import by.it_academy.task.core.ProjectStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.dao.converter.UserRefConverter;
import by.it_academy.task.dao.converter.UserRefListConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
    @Converts({
            @Convert(attributeName = "manager", converter = UserRefConverter.class),
            @Convert(attributeName = "stuff", converter = UserRefListConverter.class)
    })
@Table(name="project")
public class ProjectEntity {

    @Id
    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "dt_create", nullable = false)
    private LocalDateTime dtCreate;

    @Version
    @Column(name = "dt_update", nullable = false)
    private LocalDateTime dtUpdate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "manager", nullable = false)
    private UserRef manager;

    @Column(name = "stuff", nullable = false)
    private List<UserRef> stuff;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    public ProjectEntity() {
    }

    public ProjectEntity(UUID projectId, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, String description,
                         UserRef manager, List<UserRef> stuff, ProjectStatus status) {
        this.projectId = projectId;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.stuff = stuff;
        this.status = status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public ProjectEntity setProjectId(UUID projectId) {
        this.projectId = projectId;
        return this;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public ProjectEntity setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public ProjectEntity setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProjectEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProjectEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserRef getManager() {
        return manager;
    }

    public ProjectEntity setManager(UserRef manager) {
        this.manager = manager;
        return this;
    }

    public List<UserRef> getStuff() {
        return stuff;
    }

    public ProjectEntity setStuff(List<UserRef> stuff) {
        this.stuff = stuff;
        return this;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public ProjectEntity setStatus(ProjectStatus status) {
        this.status = status;
        return this;
    }
}
