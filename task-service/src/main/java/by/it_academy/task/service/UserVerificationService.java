package by.it_academy.task.service;

import by.it_academy.task.controller.utils.JwtTokenHandler;
import by.it_academy.task.core.dto.UserSimpleViewWithId;
import by.it_academy.task.service.api.IUserVerificationService;
import by.it_academy.task.service.feign.IUserClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserVerificationService implements IUserVerificationService {

    private final IUserClient userClient;
    private final JwtTokenHandler tokenHandler;
    private final UserHolder userHolder;

    public UserVerificationService(IUserClient userClient, JwtTokenHandler tokenHandler, UserHolder userHolder) {
        this.userClient = userClient;
        this.tokenHandler = tokenHandler;
        this.userHolder = userHolder;
    }

    @Override
    public boolean existsById(UUID id) {
        String token = tokenHandler.getJwtToken(userHolder.getUser());
        UserSimpleViewWithId user = userClient.getUser(id, token);
        return user != null;
    }
}
