package by.it_academy.task.service;

import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.dto.TaskCreate;
import by.it_academy.task.core.dto.TaskView;
import by.it_academy.task.dao.api.ITaskDao;
import by.it_academy.task.dao.entity.TaskEntity;
import by.it_academy.task.service.api.IProjectService;
import by.it_academy.task.service.api.ITaskService;
import by.it_academy.task.service.exception.TaskNotFoundException;
import by.it_academy.task.service.exception.TaskServiceException;
import by.it_academy.task.service.exception.TaskVersionException;
import by.it_academy.task.util.TPage;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskService implements ITaskService {
    private static final String TASK_NOT_FOUND = "Задача не найдена.";
    private static final String TASK_VERSION_MISMATCH = "Информация о задаче была обновлена ранее, проверьте актуальную версию.";
    private static final String TASK_CREATED = "Создана новая задача.";
    private static final String TASK_UPDATED = "Информация о задаче обновлена.";
    private static final String TASK_STATUS_UPDATED = "Статус задачи обновлен.";

    private final ITaskDao taskDao;
    private final IProjectService projectService;

    public TaskService(ITaskDao taskDao, IProjectService projectService) {
        this.taskDao = taskDao;
        this.projectService = projectService;
    }

    @Transactional
    @Override
    public void save(TaskCreate taskCreate) {
        TaskEntity entity = new TaskEntity();
        entity.setTaskId(UUID.randomUUID());
        entity.setDtCreate(LocalDateTime.now());
        entity.setDtUpdate(entity.getDtCreate());

        entity.setProject(taskCreate.getProject());
        entity.setTitle(taskCreate.getTitle());
        entity.setDescription(taskCreate.getDescription());
        entity.setImplementer(taskCreate.getImplementer());
        entity.setStatus(taskCreate.getStatus());
        taskDao.saveAndFlush(entity);

        //todo audit
    }

    @Transactional
    @Override
    public void update(TaskCreate taskCreate, UUID uuid, LocalDateTime dtUpdate) {
        TaskEntity entity = taskDao.findById(uuid).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND, "uuid"));
        if(!entity.getDtUpdate().equals(dtUpdate)) {
            throw new TaskVersionException(TASK_VERSION_MISMATCH, "dt_update");
        }
        taskDao.delete(entity);

        entity.setProject(taskCreate.getProject());
        entity.setTitle(taskCreate.getTitle());
        entity.setDescription(taskCreate.getDescription());
        entity.setImplementer(taskCreate.getImplementer());
        entity.setStatus(taskCreate.getStatus());
        taskDao.saveAndFlush(entity);

        //todo audit
    }

    @Transactional(readOnly = true)
    @Override
    public TPage<TaskView> getPage(int page, int size) {
        TPage<TaskView> pageResult = new TPage<>();
        List<TaskEntity> allEntities = taskDao.findAll();
        int countEntities = allEntities.size();
        int countPages = (int) Math.ceil(countEntities / (double) size );

        if(page > countPages) {
            page = countPages -1;
        }
        pageResult.setNumber(page);
        pageResult.setTotal_pages(countPages);
        pageResult.setSize(size);
        pageResult.setTotal_element(countEntities);
        pageResult.setFirst(page == 0);
        pageResult.setLast(page == countPages - 1);
        int count = 0;
        pageResult.setContent(new ArrayList<>());
        for(int i = page * size; i <= (page + 1) * size && i < countEntities; i++) {
            pageResult.addElement(entityToDto(allEntities.get(i)));
            count++;
        }
        pageResult.setNumber_of_elements(count);
        return pageResult;
    }

    @Transactional(readOnly = true)
    @Override
    public TaskView getTask(UUID uuid) {
        TaskEntity entity = taskDao.findById(uuid).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND, "uuid"));
        return entityToDto(entity);
    }

    @Transactional
    @Override
    public void updateStatus(UUID uuid, LocalDateTime dtUpdate, String status) {
        TaskEntity entity = taskDao.findById(uuid).orElseThrow(() -> new TaskNotFoundException(TASK_NOT_FOUND, "uuid"));
        if(!entity.getDtUpdate().equals(dtUpdate)) {
            throw new TaskVersionException(TASK_VERSION_MISMATCH, "dt_update");
        }
        taskDao.delete(entity);

        entity.setStatus(TaskStatus.valueOf(status));
        taskDao.saveAndFlush(entity);
    }

    private TaskView entityToDto(TaskEntity entity) {
        TaskView.TaskViewBuilder builder = new TaskView.TaskViewBuilder();
        builder.setTaskId(entity.getTaskId()).
                setDtCreate(entity.getDtCreate()).
                setDtUpdate(entity.getDtUpdate()).
                setProject(entity.getProject()).
                setTitle(entity.getTitle()).
                setDescription(entity.getDescription()).
                setImplementer(entity.getImplementer()).
                setStatus(entity.getStatus());

        return builder.build();
    }
}
