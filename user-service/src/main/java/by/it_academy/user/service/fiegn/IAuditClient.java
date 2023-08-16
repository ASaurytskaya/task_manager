package by.it_academy.user.service.fiegn;

import by.it_academy.user.core.dto.AuditCreate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "audit-client", url= "http://user-service:8080/api/v1/audit")
public interface IAuditClient {
    @PostMapping
    void save(@RequestBody AuditCreate auditCreate);

}