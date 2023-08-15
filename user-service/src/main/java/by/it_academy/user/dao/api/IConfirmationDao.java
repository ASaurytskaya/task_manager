package by.it_academy.user.dao.api;

import by.it_academy.user.dao.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IConfirmationDao extends JpaRepository<ConfirmationEntity, UUID> {

    boolean existsByMail(String mail);

    ConfirmationEntity findByMail(String mail);
}
