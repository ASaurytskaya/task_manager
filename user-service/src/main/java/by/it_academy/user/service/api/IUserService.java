package by.it_academy.user.service.api;

import by.it_academy.user.core.dto.UserSimpleViewWithPass;
import by.it_academy.user.core.dto.UserView;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.util.TPage;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    UserEntity save(UserSimpleViewWithPass userCreate);

    UserEntity get(UUID uuid);

    void delete(UUID uuid, LocalDateTime dtUpdate);

    UserEntity update(UserSimpleViewWithPass userUpdate, UUID uuid, LocalDateTime dtUpdate);

    TPage<UserView> getPage(int page, int size);

    UserView entityToDto(UserEntity entity);

    boolean existsByMail(String mail);

    UserEntity findByMail(String mail);
}
