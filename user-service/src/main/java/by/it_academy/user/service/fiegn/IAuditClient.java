package by.it_academy.user.service.fiegn;

import by.it_academy.user.core.dto.AuditCreate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "audit-client", url= "localhost:82/api/v1/audit")
public interface IAuditClient {
    @PostMapping
    void save(@RequestBody AuditCreate auditCreate, @RequestHeader(name = "Authorization") String token);

}
