package by.it_academy.user.service;

import by.it_academy.user.dao.api.IConfirmationDao;
import by.it_academy.user.dao.entity.ConfirmationEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationService {
    private final IConfirmationDao confirmationDao;

    public ConfirmationService(IConfirmationDao confirmationDao) {
        this.confirmationDao = confirmationDao;
    }

    public void save(ConfirmationEntity entity) {
        confirmationDao.save(entity);
    }

    public void confirmMail(String code, String mail) {
        ConfirmationEntity entity = null;
        try{
             entity = confirmationDao.findByMail(mail);
        } catch(RuntimeException e) {
            //ToDo exception
            throw new RuntimeException(e.getMessage());
        }

        if(entity.getDtCreate().plusMinutes(30).isBefore(LocalDateTime.now())) {
            confirmationDao.delete(entity);
            //TODO new exception code exp.
            throw new RuntimeException();
        }
        if(!code.equals(entity.getCode())) {
            //TODO new exception code exp.
            throw new RuntimeException();
        }
    }
}
