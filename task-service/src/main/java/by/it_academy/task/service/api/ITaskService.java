package by.it_academy.task.service.api;

import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.dto.TaskCreate;
import by.it_academy.task.core.dto.TaskView;
import by.it_academy.task.util.TPage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ITaskService {
    void save(TaskCreate taskCreate);

    void update(TaskCreate taskCreate, UUID uuid, LocalDateTime dtUpdate);

    TPage<TaskView> getPage(int page, int size);

    TaskView getTask(UUID uuid);

    void updateStatus(UUID uuid, LocalDateTime dtUpdate, String status);
}
