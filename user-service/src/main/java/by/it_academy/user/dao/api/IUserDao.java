package by.it_academy.user.dao.api;

import by.it_academy.user.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<UserEntity, UUID> {


    boolean existsByMail(String mail);

    UserEntity findByMail(String mail);
}
