package by.it_academy.audit.service;

import by.it_academy.audit.core.EssenceType;
import by.it_academy.audit.core.dto.Audit;
import by.it_academy.audit.core.dto.AuditCreate;
import by.it_academy.audit.core.dto.CustomUserDetails;
import by.it_academy.audit.core.dto.User;
import by.it_academy.audit.dao.api.IAuditDao;
import by.it_academy.audit.dao.entity.AuditEntity;
import by.it_academy.audit.service.api.IAuditService;
import by.it_academy.audit.service.exception.AuditNotFoundException;
import by.it_academy.audit.util.TPage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuditService implements IAuditService {
    private static final String AUDIT_NOT_FOUND = "Запись не найдена.";

    private final IAuditDao auditDao;
    private final UserFinderService userFinderService;


    public AuditService(IAuditDao auditDao, UserFinderService userFinderService) {
        this.auditDao = auditDao;
        this.userFinderService = userFinderService;
    }

    @Override
    public void save(AuditCreate auditCreate, String token) {
       CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       // User user = userFinderService.getMe(token);

        AuditEntity entity = new AuditEntity();
        entity.setDtCreate(LocalDateTime.now());
        entity.setUserId(user.getId());
        entity.setMail(user.getMail());
        entity.setFio(user.getFio());
        entity.setRole(user.getUserRole());
        entity.setText(auditCreate.getText());
        entity.setType(auditCreate.getType());
        entity.setId(auditCreate.getId());

        auditDao.save(entity);
    }


    @Override
    public TPage<Audit> getPage(int page, int size) {
        TPage<Audit> pageResult = new TPage<>();
        List<AuditEntity> allEntities = auditDao.findAll();

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
        pageResult.setLast(countPages == 0 || page == countPages - 1);
        int count = 0;
        pageResult.setContent(new ArrayList<>());
        for(int i = page * size; i <= (page + 1) * size && i < countEntities; i++) {
            pageResult.addElement(entityToDto(allEntities.get(i)));
            count++;
        }
        pageResult.setNumber_of_elements(count);
        return pageResult;
    }

    @Override
    public Audit getAudit(UUID uuid) {
        AuditEntity entity = auditDao.findById(uuid).
                orElseThrow(() -> new AuditNotFoundException(AUDIT_NOT_FOUND, "uuid"));

        return entityToDto(entity);
    }

    private Audit entityToDto(AuditEntity entity) {
        User user = new User(entity.getUserId(), entity.getMail(), entity.getFio(), entity.getRole());

        Audit.AuditBuilder builder = new Audit.AuditBuilder();
        Audit audit = builder.setUuid(entity.getUuid()).
                setDtCreate(entity.getDtCreate()).
                setText(entity.getText()).
                setType(entity.getType()).
                setId(entity.getId()).
                setUser(user).build();
        return audit;
    }
}
