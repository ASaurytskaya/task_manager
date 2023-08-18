package by.it_academy.audit.service;

import by.it_academy.audit.core.dto.User;
import by.it_academy.audit.service.fiegn.IUserClient;
import org.springframework.stereotype.Service;

@Service
public class UserFinderService {
    private IUserClient userClient;

    public UserFinderService(IUserClient userClient) {
        this.userClient = userClient;
    }

    public User getMe(String token) {
        return this.userClient.getMe("Bearer " + token).getBody();
    }
}
