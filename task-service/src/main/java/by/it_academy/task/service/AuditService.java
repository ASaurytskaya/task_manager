package by.it_academy.task.service;

import by.it_academy.task.controller.utils.JwtTokenHandler;
import by.it_academy.task.core.dto.AuditCreate;
import by.it_academy.task.core.dto.CustomUserDetails;
import by.it_academy.task.core.dto.UserSimpleViewWithId;
import by.it_academy.task.service.feign.IAuditClient;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AuditService{
    private final IAuditClient auditClient;
    private final JwtTokenHandler tokenHandler;

    public AuditService(IAuditClient auditClient, JwtTokenHandler tokenHandler) {
        this.auditClient = auditClient;
        this.tokenHandler = tokenHandler;
    }

    public void saveToAudit(CustomUserDetails user, String text, String type, UUID uuid) {
        AuditCreate auditCreate = new AuditCreate();

        UserSimpleViewWithId userSimple = new UserSimpleViewWithId();
        userSimple.setUuid(user.getId());
        userSimple.setMail(user.getMail());
        userSimple.setFio(user.getFio());
        userSimple.setUserRole(user.getUserRole());
        userSimple.setUserStatus(user.getUserStatus());

        auditCreate.setUser(userSimple);
        auditCreate.setText(text);
        auditCreate.setType(type);
        auditCreate.setId(uuid.toString());

        String token = tokenHandler.getJwtToken(user);

        auditClient.save(auditCreate, token);
    }
}
