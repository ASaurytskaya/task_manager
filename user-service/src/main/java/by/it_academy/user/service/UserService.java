package by.it_academy.user.service;

import by.it_academy.user.core.dto.*;
import by.it_academy.user.core.dto.UserSimpleViewWithPass;
import by.it_academy.user.dao.api.IUserDao;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.api.IUserService;
import by.it_academy.user.service.exceptions.UserInfoChangeException;
import by.it_academy.user.service.exceptions.UserMailDuplicateException;
import by.it_academy.user.service.exceptions.UserNotFoundException;
import by.it_academy.user.util.TPage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private static final String USER_DATA_CHANGED = "Информация о пользователе была изменена ранее. Проверьте актуальность используемых данных.";
    private static final String MAIL_DUPLICATED = "Эта почта уже используется. Введите другой адрес.";
    private static final String USER_NOT_FOUND = "Пользователь не найден.";

    private static final String USER_CREATED  = "Создан новый пользователь";
    private static final String USER_UPDATED = "Обновлена информация о пользователе";


    private final IUserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    public UserService(IUserDao userDao, PasswordEncoder passwordEncoder, AuditService auditService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.auditService = auditService;
    }

    @Transactional
    @Override
    public UserEntity save(UserSimpleViewWithPass userCreate) {
        UserEntity entity = new UserEntity();

        UUID id = UUID.randomUUID();

        while(userDao.existsById(id)) {
            id = UUID.randomUUID();
        }

        String mail = userCreate.getMail();
        if(userDao.existsByMail(mail)) {
            throw new UserMailDuplicateException(MAIL_DUPLICATED, "mail");
        }

        entity.setUserId(id);
        entity.setDtCreate(LocalDateTime.now());
        entity.setDtUpdate(LocalDateTime.now());
        entity.setMail(userCreate.getMail());
        entity.setFio(userCreate.getFio());
        entity.setPassword(userCreate.getPassword());
        entity.setUserRole(userCreate.getUserRole());
        entity.setUserStatus(userCreate.getUserStatus());

        UserEntity returnedEntity = userDao.saveAndFlush(entity);
        auditService.saveToAudit(returnedEntity, USER_CREATED);

        return returnedEntity;
    }


    @Transactional(readOnly = true)
    @Override
    public UserEntity get(UUID uuid) {
        return userDao.findById(uuid).
                orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "uuid"));
    }

    @Transactional
    @Override
    public void delete(UUID uuid, LocalDateTime dtUpdate) {

        UserEntity entity = userDao.findById(uuid).
                orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "uuid"));

        if(!dtUpdate.isEqual(entity.getDtUpdate())) {
            throw new UserInfoChangeException(USER_DATA_CHANGED, "dt_update");
        }

        userDao.delete(entity);
    }

    @Transactional
    @Override
    public UserEntity update(UserSimpleViewWithPass user, UUID uuid, LocalDateTime dtUpdate) {
        UserEntity entity = userDao.findById(uuid).
                orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "uuid"));

        if(!entity.getDtUpdate().equals(dtUpdate)) {
            throw new UserInfoChangeException(USER_DATA_CHANGED, "dt_update");
        }

        UserEntity newEntity = new UserEntity();
        newEntity.setUserId(uuid);
        newEntity.setDtCreate(entity.getDtCreate());
        newEntity.setDtUpdate(LocalDateTime.now());

        newEntity.setMail(user.getMail());
        newEntity.setFio(user.getFio());
        newEntity.setUserRole(user.getUserRole());
        newEntity.setUserStatus(user.getUserStatus());

        String pass = user.getPassword();
        if(!pass.equals(entity.getPassword())) {
            pass = passwordEncoder.encode(pass);
        }
        newEntity.setPassword(pass);

        userDao.delete(entity);
        UserEntity returnedEntity = userDao.saveAndFlush(newEntity);
        auditService.saveToAudit(returnedEntity, USER_UPDATED);

        return returnedEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public TPage<UserView> getPage(int page, int size) {
        TPage<UserView> pageResult = new TPage<>();
        List<UserEntity> allEntities = userDao.findAll();
        int countEntities = allEntities.size();
        int countPages = (int) Math.ceil(countEntities / (double) size );

        if(page > countPages) {
            page = countPages -1;
        }
        pageResult.setNumber(page);
        pageResult.setTotal_pages(countPages);
        pageResult.setSize(size);
        pageResult.setTotal_element(countEntities);
        pageResult.setFirst(page == 0);
        pageResult.setLast(countPages == 0 || page == countPages - 1);
        int count = 0;
        pageResult.setContent(new ArrayList<>());
        for(int i = page * size; i <= (page + 1) * size && i < countEntities; i++) {
            pageResult.addElement(entityToDto(allEntities.get(i)));
            count++;
        }
        pageResult.setNumber_of_elements(count);
        return pageResult;
    }

    @Override
    public UserView entityToDto(UserEntity userEntity) {
        UserView user = new UserView.UserViewBuilder().
                setUuid(userEntity.getUserId()).
                setDtCreate(userEntity.getDtCreate()).
                setDtUpdate(userEntity.getDtUpdate()).
                setMail(userEntity.getMail()).
                setFio(userEntity.getFio()).
                setUserRole(userEntity.getUserRole()).
                setUserStatus(userEntity.getUserStatus()).
                build();
        return user;
    }

    @Override
    public boolean existsByMail(String mail) {
        return userDao.existsByMail(mail);
    }

    @Override
    public UserEntity findByMail(String mail) {
        if(!existsByMail(mail)) {
            throw new UserNotFoundException(USER_NOT_FOUND, "mail");
        }
        return userDao.findByMail(mail);
    }
}
