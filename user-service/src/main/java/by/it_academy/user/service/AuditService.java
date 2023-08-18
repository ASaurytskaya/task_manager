package by.it_academy.user.service;

import by.it_academy.user.core.EssenceType;
import by.it_academy.user.core.dto.AuditCreate;
import by.it_academy.user.core.dto.UserSimpleViewWithId;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.fiegn.IAuditClient;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final IAuditClient auditClient;

    public AuditService(IAuditClient auditClient) {
        this.auditClient = auditClient;
    }

    //TODO values
    public void saveToAudit(UserEntity entity, String text) {
        UserSimpleViewWithId user = new UserSimpleViewWithId();
        user.setUuid(entity.getUserId());
        user.setMail(entity.getMail());
        user.setFio(entity.getFio());
        user.setUserRole(entity.getUserRole());

        AuditCreate auditCreate = new AuditCreate();
        auditCreate.setUser(user);
        auditCreate.setText(text);
        auditCreate.setType(EssenceType.USER);
        auditCreate.setId(entity.getUserId().toString());

        auditClient.save(auditCreate);
    }
}
