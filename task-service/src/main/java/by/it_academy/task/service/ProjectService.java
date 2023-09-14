package by.it_academy.task.service;

import by.it_academy.task.core.ProjectStatus;
import by.it_academy.task.core.UserRef;
import by.it_academy.task.core.UserRole;
import by.it_academy.task.core.dto.ProjectCreate;
import by.it_academy.task.core.dto.ProjectView;
import by.it_academy.task.dao.api.IProjectDao;
import by.it_academy.task.dao.entity.ProjectEntity;
import by.it_academy.task.dao.specification.ProjectSpecifications;
import by.it_academy.task.service.api.IProjectService;
import by.it_academy.task.service.api.IUserVerificationService;
import by.it_academy.task.service.exception.ProjectNotFoundException;
import by.it_academy.task.service.exception.ProjectVersionException;
import by.it_academy.task.service.exception.UserNotFoundException;
import by.it_academy.task.util.TPage;
import org.springframework.data.jpa.domain.Specification;
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
    private static final String USER_NOT_FOUND = "Пользователь не найден.";

    private static final String PROJECT_CREATED = "Создан новый проект.";
    private static final String PROJECT_UPDATED = "Информация о проекте обновлена.";
    private static final String PROJECT_TYPE = "PROJECT";


    private final IProjectDao projectDao;
    private final AuditService auditService;
    private final UserHolder userHolder;
    private final IUserVerificationService userVerificationService;

    public ProjectService(IProjectDao projectDao, AuditService auditService, UserHolder userHolder,
                          IUserVerificationService userVerificationService) {
        this.projectDao = projectDao;
        this.auditService = auditService;
        this.userHolder = userHolder;
        this.userVerificationService = userVerificationService;
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

        try{
            userVerificationService.existsById(projectCreate.getManager().getUuid());
        } catch(RuntimeException e) {
            throw new UserNotFoundException(USER_NOT_FOUND, "uuid");
        }
        entity.setManager(projectCreate.getManager());

        List<ProjectEntity.Staff> staffList = new ArrayList<>();
        try{
            for (UserRef userRef : projectCreate.getStaff()) {
                userVerificationService.existsById(userRef.getUuid());
                staffList.add(new ProjectEntity.Staff(userRef.getUuid()));
            }
        } catch(RuntimeException e) {
            throw new UserNotFoundException(USER_NOT_FOUND, "uuid");
        }
        entity.setStaff(staffList);

        entity.setStatus(projectCreate.getStatus());
        projectDao.saveAndFlush(entity);

        auditService.saveToAudit(userHolder.getUser(), PROJECT_CREATED, PROJECT_TYPE, entity.getProjectId());
    }

    @Transactional
    @Override
    public void update(ProjectCreate projectCreate, UUID uuid, LocalDateTime dtUpdate) {
        ProjectEntity entity = projectDao.findById(uuid).orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND, "uuid"));
        if(!entity.getDtUpdate().equals(dtUpdate)) {
            throw  new ProjectVersionException(PROJECT_VERSION_MISMATCH, "dt_update");
        }

        projectDao.delete(entity);
        ProjectEntity newEntity = new ProjectEntity();
        newEntity.setProjectId(entity.getProjectId());
        newEntity.setDtCreate(entity.getDtCreate());
        newEntity.setDtUpdate(LocalDateTime.now());
        newEntity.setName(projectCreate.getName());
        newEntity.setDescription(projectCreate.getDescription());

        try{
            userVerificationService.existsById(projectCreate.getManager().getUuid());
        } catch(RuntimeException e) {
            throw new UserNotFoundException(USER_NOT_FOUND, "uuid");
        }
        newEntity.setManager(projectCreate.getManager());


        List<ProjectEntity.Staff> staffList = new ArrayList<>();
        try{
            for (UserRef userRef : projectCreate.getStaff()) {
                userVerificationService.existsById(userRef.getUuid());
                staffList.add(new ProjectEntity.Staff(userRef.getUuid()));
            }
        } catch(RuntimeException e) {
            throw new UserNotFoundException(USER_NOT_FOUND, "uuid");
        }
        newEntity.setStaff(staffList);

        newEntity.setStatus(projectCreate.getStatus());

        projectDao.saveAndFlush(newEntity);

        auditService.saveToAudit(userHolder.getUser(), PROJECT_UPDATED, PROJECT_TYPE, entity.getProjectId());
    }

    @Transactional(readOnly = true)
    @Override
    public TPage<ProjectView> getPage(int page, int size, boolean archived) {

        Specification<ProjectEntity> specification = Specification.where(null);
        if(!userHolder.getUser().getUserRole().equals(UserRole.ADMIN)) {
            specification = specification.and(ProjectSpecifications.hasUserInStaff(userHolder.getUser().getId()));
        }
        if(!archived) {
            specification  = specification.and(Specification.not(ProjectSpecifications.hasStatus(ProjectStatus.ARCHIVED)));
        }
        List<ProjectEntity> allEntities = projectDao.findAll(specification);

        TPage<ProjectView> pageResult = new TPage<>();

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
    public ProjectView getProject(UUID uuid) {
        ProjectEntity entity = projectDao.findById(uuid).orElseThrow(() -> new ProjectNotFoundException(PROJECT_NOT_FOUND, "uuid"));
        return entityToDto(entity);
    }

    @Override
    public List<UUID> getProjectsList() {
        List<ProjectEntity> entities = getMyProjects();
        List<UUID> list = new ArrayList<>();
        for (ProjectEntity entity : entities) {
            list.add(entity.getProjectId());
        }
        return list;
    }

    private List<ProjectEntity> getMyProjects() {
        Specification<ProjectEntity> specification = Specification.where(null);
        if(!userHolder.getUser().getUserRole().equals(UserRole.ADMIN)) {
            specification = specification.and(ProjectSpecifications.hasUserInStaff(userHolder.getUser().getId()));
        }
        List<ProjectEntity> allEntities = projectDao.findAll(specification);
        return allEntities;
    }


    private ProjectView entityToDto(ProjectEntity entity) {
        List<UserRef> userRefList = new ArrayList<>();
        for (ProjectEntity.Staff staff : entity.getStaff()) {
            userRefList.add(new UserRef(staff.getUserId()));
        }

        ProjectView.ProjectViewBuilder builder = new ProjectView.ProjectViewBuilder();
        builder.setUuid(entity.getProjectId()).
                setDtCreate(entity.getDtCreate()).
                setDtUpdate(entity.getDtUpdate()).
                setName(entity.getName()).
                setDescription(entity.getDescription()).
                setManager(entity.getManager()).
                setStuff(userRefList).
                setStatus(entity.getStatus());

        return builder.build();
    }
}
