package by.it_academy.report.service;

import by.it_academy.report.core.dto.Audit;
import by.it_academy.report.core.dto.CustomUserDetails;
import by.it_academy.report.core.dto.ReportParamAudit;
import by.it_academy.report.service.feign.IAuditClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    private final IAuditClient auditClient;
    private final JwtTokenHandler tokenHandler;

    public AuditService(IAuditClient auditClient, JwtTokenHandler tokenHandler) {
        this.auditClient = auditClient;
        this.tokenHandler = tokenHandler;
    }

    public List<Audit> getList(ReportParamAudit reportParamAudit) {
        String token = tokenHandler.getJwtToken((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Audit> list = auditClient.getList(reportParamAudit, token);
        return list;
    }
}
