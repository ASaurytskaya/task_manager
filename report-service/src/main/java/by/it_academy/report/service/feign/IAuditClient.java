package by.it_academy.report.service.feign;


import by.it_academy.report.core.dto.Audit;
import by.it_academy.report.core.dto.ReportParamAudit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "audit-client", url= "localhost:82/api/v1/audit/report")
public interface IAuditClient {
    @PostMapping
    List<Audit> getList(@RequestBody ReportParamAudit param, @RequestHeader("Authorization") String token);

}
