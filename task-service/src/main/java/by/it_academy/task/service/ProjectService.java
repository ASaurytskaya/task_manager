package by.it_academy.task.service;

import by.it_academy.task.core.dto.ProjectCreate;
import by.it_academy.task.core.dto.ProjectView;
import by.it_academy.task.dao.api.IProjectDao;
import by.it_academy.task.dao.entity.ProjectEntity;
import by.it_academy.task.service.api.IProjectService;
import by.it_academy.task.service.exception.ProjectNotFoundException;
import by.it_academy.task.service.exception.ProjectVersionException;
import by.it_academy.task.util.TPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService implements IProjectService {
    private static final String PROJECT_NOT_FOUND = "Проект не найден.";
    private static final String PROJECT_VERSION_MISMATCH = "Информация о проекте была обновлена ранее, проверьте актуальную версию проекта.";
    private static final String PROJECT_CREATED = "Создан новый проект.";
    private static final String PROJECT_UPDATED = "Информация о проекте обновлена.";


    private final IProjectDao projectDao;

    public ProjectService(IProjectDao projectDao, UserVerificationService userVerificationService) {
        this.projectDao = projectDao;
    }

    @Transactional
    @Override
    public void save(ProjectCreate projectCreate) {
        ProjectEntity entity = new ProjectEntity();
        entity.setProjectId(UUID.randomUUID());
        entity.setDtCreate(LocalDateTime.now());
        entity.setDtUpdate(entity.getDtCreate());

        entity.setName(projectCreate.getName());
        entity.setDescription(projectCreate.getDescription());
        entity.setManager(projectCreate.getManager());
        entity.setStuff(projectCreate.getStuff());
        entity.setStatus(projectCreate.getStatus());
        projectDao.saveAndFlush(entity);

        //todo audit
    }

    @Transactional
    @Override
    public void update(ProjectCreate projectCreate, UUID uuid, LocalDateTime dtUpdate) {
        ProjectEntity entity = projectDao.findById(uuid).orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND, "uuid"));
        if(!entity.getDtUpdate().equals(dtUpdate)) {
            throw  new ProjectVersionException(PROJECT_VERSION_MISMATCH, "dt_update");
        }

        projectDao.delete(entity);

        entity.setName(projectCreate.getName());
        entity.setDescription(projectCreate.getDescription());
        entity.setManager(projectCreate.getManager());
        entity.setStuff(projectCreate.getStuff());
        entity.setStatus(projectCreate.getStatus());

        projectDao.saveAndFlush(entity);

        //todo audit
    }

    @Transactional(readOnly = true)
    @Override
    public TPage<ProjectView> getPage(int page, int size) {
        TPage<ProjectView> pageResult = new TPage<>();
        List<ProjectEntity> allEntities = projectDao.findAll();
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
    public ProjectView getProject(UUID uuid) {
        ProjectEntity entity = projectDao.findById(uuid).orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND, "uuid"));
        return entityToDto(entity);
    }

    private ProjectView entityToDto(ProjectEntity entity) {
        ProjectView.ProjectViewBuilder builder = new ProjectView.ProjectViewBuilder();
        builder.setProjectId(entity.getProjectId()).
                setDtCreate(entity.getDtCreate()).
                setDtUpdate(entity.getDtUpdate()).
                setName(entity.getName()).
                setDescription(entity.getDescription()).
                setManager(entity.getManager()).
                setStuff(entity.getStuff()).
                setStatus(entity.getStatus());

        return builder.build();
    }
}
