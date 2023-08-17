package by.it_academy.audit.service.api;

import by.it_academy.audit.core.dto.Audit;
import by.it_academy.audit.core.dto.AuditCreate;
import by.it_academy.audit.util.TPage;

import java.util.UUID;

public interface IAuditService {
    void save(AuditCreate auditCreate, String token);

    TPage<Audit> getPage(int page, int size);

    Audit getAudit(UUID uuid);
}
