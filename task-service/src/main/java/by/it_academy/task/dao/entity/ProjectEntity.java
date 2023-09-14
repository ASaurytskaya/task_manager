package by.it_academy.task.dao.entity;

import by.it_academy.task.core.ProjectStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.dao.converter.UserRefConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
    @Converts({
            @Convert(attributeName = "manager", converter = UserRefConverter.class)
    })
@Table(name = "project")
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

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ElementCollection
    @CollectionTable(name = "staff", joinColumns = @JoinColumn(name = "project_id"))
    private List<Staff> staff = new ArrayList<>();

    public ProjectEntity() {
    }

    public ProjectEntity(UUID projectId, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, String description,
                         UserRef manager, ProjectStatus status) {
        this.projectId = projectId;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.status = status;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRef getManager() {
        return manager;
    }

    public void setManager(UserRef manager) {
        this.manager = manager;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    @Embeddable
    public static class Staff {
        @Column(name = "user_id", nullable = false)
        private UUID userId;

        public Staff() {
        }

        public Staff(UUID userId) {
            this.userId = userId;
        }

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Staff staff)) return false;
            return getUserId().equals(staff.getUserId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getUserId());
        }
    }
}
