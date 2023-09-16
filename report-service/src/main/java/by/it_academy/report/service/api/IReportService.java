package by.it_academy.report.service.api;

import by.it_academy.report.core.ReportStatus;
import by.it_academy.report.core.ReportType;
import by.it_academy.report.core.dto.Report;
import by.it_academy.report.core.dto.ReportParamAudit;
import by.it_academy.report.util.TPage;
import org.springframework.core.io.Resource;

import java.util.UUID;

public interface IReportService {
    void save(ReportType type, ReportParamAudit param);

    TPage<Report> getPage(int page, int size);

    ReportStatus getStatus(UUID uuid);

    Report getReport(UUID uuid);

    Resource getFile(UUID uuid);
}
