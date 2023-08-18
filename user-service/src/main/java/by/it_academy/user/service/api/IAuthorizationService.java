package by.it_academy.user.service.api;

import by.it_academy.user.core.dto.*;

public interface IAuthorizationService {

    String register(UserRegistration userRegistration);

    void verify(MailVerification userVerification);

    String logIn(UserLogin userLogin);

    UserView about();
}
