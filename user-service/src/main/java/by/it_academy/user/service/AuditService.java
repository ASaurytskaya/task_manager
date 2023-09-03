package by.it_academy.user.service;

import by.it_academy.user.controller.utils.JwtTokenHandler;
import by.it_academy.user.core.EssenceType;
import by.it_academy.user.core.dto.AuditCreate;
import by.it_academy.user.core.dto.CustomUserDetails;
import by.it_academy.user.core.dto.UserSimpleViewWithId;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.fiegn.IAuditClient;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private final IAuditClient auditClient;
    private final JwtTokenHandler tokenHandler;
    private final UserHolder userHolder;

    public AuditService(IAuditClient auditClient, JwtTokenHandler tokenHandler, UserHolder userHolder) {
        this.auditClient = auditClient;
        this.tokenHandler = tokenHandler;
        this.userHolder = userHolder;
    }

    //TODO values
    public void saveToAudit(UserEntity entity, String text) {
        UserSimpleViewWithId user = new UserSimpleViewWithId();
        user.setUuid(entity.getUserId());
        user.setMail(entity.getMail());
        user.setFio(entity.getFio());
        user.setUserRole(entity.getUserRole());

//todo info from token only
        AuditCreate auditCreate = new AuditCreate();
        auditCreate.setUser(user);
        auditCreate.setText(text);
        auditCreate.setType(EssenceType.USER);
        auditCreate.setId(entity.getUserId().toString());

        CustomUserDetails ud = new CustomUserDetails.CustomUserDetailsBuilder().
                setId(entity.getUserId()).
                setMail(entity.getMail()).
                setFio(entity.getFio()).
                setUserRole(entity.getUserRole()).
                setUserStatus(entity.getUserStatus()).build();
        String token = tokenHandler.getJwtToken(ud);

        auditClient.save(auditCreate, token);
    }
}
