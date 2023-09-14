package by.it_academy.task.service.api;

import by.it_academy.task.core.dto.ProjectCreate;
import by.it_academy.task.core.dto.ProjectView;
import by.it_academy.task.util.TPage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IProjectService {
    void save(ProjectCreate projectCreate);

    void update(ProjectCreate projectCreate,  UUID uuid, LocalDateTime dtUpdate);

    TPage<ProjectView> getPage(int page, int size, boolean archived);

    ProjectView getProject(UUID uuid);

    List<UUID> getProjectsList();

}
