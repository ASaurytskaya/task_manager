package by.it_academy.task.service;

import by.it_academy.task.core.ProjectRef;
import by.it_academy.task.core.TaskStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.core.dto.TaskCreate;
import by.it_academy.task.core.dto.TaskView;
import by.it_academy.task.dao.api.ITaskDao;
import by.it_academy.task.dao.entity.TaskEntity;
import by.it_academy.task.dao.specification.TaskSpecifications;
import by.it_academy.task.service.api.IProjectService;
import by.it_academy.task.service.api.ITaskService;
import by.it_academy.task.service.exception.TaskNotFoundException;
import by.it_academy.task.service.exception.TaskVersionException;
import by.it_academy.task.service.feign.IAuditClient;
import by.it_academy.task.util.TPage;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {
    private static final String TASK_NOT_FOUND = "Задача не найдена.";
    private static final String TASK_VERSION_MISMATCH = "Информация о задаче была обновлена ранее, проверьте актуальную версию.";
    private static final String TASK_CREATED = "Создана новая задача.";
    private static final String TASK_UPDATED = "Информация о задаче обновлена.";
    private static final String TASK_STATUS_UPDATED = "Статус задачи обновлен.";
    private static final String TASK_TYPE = "TASK";


    private final ITaskDao taskDao;
    private final IProjectService projectService;
    private final UserHolder userHolder;
    private final AuditService auditService;

    public TaskService(ITaskDao taskDao, IProjectService projectService, UserHolder userHolder, AuditService auditService) {
        this.taskDao = taskDao;
        this.projectService = projectService;
        this.userHolder = userHolder;
        this.auditService = auditService;
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

        auditService.saveToAudit(userHolder.getUser(), TASK_CREATED, TASK_TYPE, entity.getTaskId());
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

        auditService.saveToAudit(userHolder.getUser(), TASK_UPDATED, TASK_TYPE, entity.getTaskId());
    }

    @Transactional(readOnly = true)
    @Override
    public TPage<TaskView> getPage(int page, int size, List<ProjectRef> project, List<UserRef> implementer, List<TaskStatus> status) {

//todo filter
        Specification<TaskEntity> specification = Specification.where(null);
        if(project != null && !project.isEmpty()) {
            specification = specification.and(TaskSpecifications.hasProject(project));
        }
        if(implementer != null && !implementer.isEmpty()) {
            specification = specification.and(TaskSpecifications.hasImplementer(implementer));
        }
        if(status != null && !status.isEmpty()) {
            specification = specification.and(TaskSpecifications.hasStatus(status));
        }

        TPage<TaskView> pageResult = new TPage<>();
        List<TaskEntity> allEntities = taskDao.findAll(specification);
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
        pageResult.setLast(page == countPages - 1 || countPages == 0);
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

        auditService.saveToAudit(userHolder.getUser(), TASK_STATUS_UPDATED, TASK_TYPE, entity.getTaskId());
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
