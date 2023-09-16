package by.it_academy.audit.service;

import by.it_academy.audit.core.dto.Audit;
import by.it_academy.audit.core.dto.ReportParamAudit;
import by.it_academy.audit.core.dto.User;
import by.it_academy.audit.dao.api.IAuditDao;
import by.it_academy.audit.dao.entity.AuditEntity;
import by.it_academy.audit.dao.specification.AuditSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelperService {

    private final IAuditDao auditDao;

    public HelperService(IAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @Transactional(readOnly = true)
    public List<Audit> getFilteredList(ReportParamAudit param) {
        Specification<AuditEntity> specification = Specification.where(null);

        if(param.getUser() != null) {
            specification  = specification.and(AuditSpecifications.hasUser(param.getUser()));
        }
        if(param.getFrom() != null) {
            specification  = specification.and(AuditSpecifications.wasFrom(param.getFrom()));
        }
        if(param.getTo() != null) {
            specification  = specification.and(AuditSpecifications.wasTo(param.getTo()));
        }

        List<AuditEntity> allEntities = auditDao.findAll(specification);

        List<Audit> resultList = new ArrayList<>();
        for (AuditEntity entity : allEntities) {
            resultList.add(entityToDto(entity));
        }

        return resultList;
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
