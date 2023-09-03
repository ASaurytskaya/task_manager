package by.it_academy.task.service;

import by.it_academy.task.core.dto.AuditCreate;
import by.it_academy.task.core.dto.UserSimpleViewWithId;
import by.it_academy.task.service.feign.IAuditClient;
import org.springframework.stereotype.Service;


@Service
public class AuditService{
    private final IAuditClient auditClient;

    public AuditService(IAuditClient auditClient) {
        this.auditClient = auditClient;
    }

    public void saveToAudit(UserSimpleViewWithId user, String text) {
        AuditCreate auditCreate = new AuditCreate();
        auditCreate.setUser(user);
        auditCreate.setText(text);
        auditCreate.setType("USER");
        auditCreate.setId(user.getUuid().toString());

        auditClient.save(auditCreate);
    }
}
