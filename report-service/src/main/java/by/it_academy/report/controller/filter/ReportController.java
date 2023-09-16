package by.it_academy.report.controller.filter;

import by.it_academy.report.core.ReportStatus;
import by.it_academy.report.core.ReportType;
import by.it_academy.report.core.dto.Report;
import by.it_academy.report.core.dto.ReportParamAudit;
import by.it_academy.report.service.api.IReportService;
import by.it_academy.report.util.TPage;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(path = "/{type}")
    public ResponseEntity<?> saveReport(@PathVariable ReportType type, @RequestBody ReportParamAudit param) {
        reportService.save(type, param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getPage( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        TPage<Report> result = reportService.getPage(page, size);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/{uuid}/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<?> getReport(@PathVariable UUID uuid) {

        Resource resource = reportService.getFile(uuid);

        if (resource == null) {
            throw new RuntimeException("File not found");
        }

        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @RequestMapping(path = "/{uuid}/export", method = RequestMethod.HEAD)
    public HttpEntity<?> getStatus(@PathVariable UUID uuid) {
        if(reportService.getStatus(uuid).equals(ReportStatus.DONE)) {
            return new HttpEntity<>(HttpStatus.OK);
        }
        return new HttpEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

}
