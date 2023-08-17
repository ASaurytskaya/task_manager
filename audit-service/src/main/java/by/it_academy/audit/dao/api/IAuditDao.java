package by.it_academy.audit.dao.api;

import by.it_academy.audit.dao.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAuditDao extends JpaRepository<AuditEntity, UUID> {
}
