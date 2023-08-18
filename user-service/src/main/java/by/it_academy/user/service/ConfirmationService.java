package by.it_academy.user.service;

import by.it_academy.user.dao.api.IConfirmationDao;
import by.it_academy.user.dao.entity.ConfirmationEntity;
import by.it_academy.user.service.exceptions.ActivationCodeInvalidException;
import by.it_academy.user.service.exceptions.TokenInvalidException;
import by.it_academy.user.service.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationService {
    private static final String USER_NOT_FOUND = "Пользователь не найден";
    private static final String TOKEN_EXPIRE = "Срок действия активационного кода истек. Пожалуйста, отправьте повторный запрос для активации почты.";
    private static final String CODE_INCORRECT = "Неверный код.";

    private final IConfirmationDao confirmationDao;

    public ConfirmationService(IConfirmationDao confirmationDao) {
        this.confirmationDao = confirmationDao;
    }

    public void save(ConfirmationEntity entity) {
        confirmationDao.save(entity);
    }

    public void confirmMail(UUID code, String mail) {
        ConfirmationEntity entity = null;
        try{
             entity = confirmationDao.findByMail(mail);
        } catch(RuntimeException e) {
            throw new UserNotFoundException(USER_NOT_FOUND, "mail");
        }

        if(entity.getDtCreate().plusMinutes(30).isBefore(LocalDateTime.now())) {
            confirmationDao.delete(entity);
            throw new TokenInvalidException(TOKEN_EXPIRE);
        }
        if(!code.equals(entity.getCode())) {
            throw new ActivationCodeInvalidException(CODE_INCORRECT);
        }
    }
}
