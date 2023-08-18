package by.it_academy.audit.controller;

import by.it_academy.audit.core.dto.Audit;
import by.it_academy.audit.core.dto.AuditCreate;
import by.it_academy.audit.service.api.IAuditService;
import by.it_academy.audit.util.TPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/audit")
public class AuditController {
    private final IAuditService auditService;

    public AuditController(IAuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AuditCreate auditCreate, @RequestHeader("Authorization") String token) {

            auditService.save(auditCreate, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestHeader("Authorization") String token) {
        TPage<Audit> auditPage = auditService.getPage(page, size);
        return new ResponseEntity<>(auditPage, HttpStatus.OK);
    }


    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> getAudit(@PathVariable UUID uuid, @RequestHeader("Authorization") String token) {

        return new ResponseEntity<>(auditService.getAudit(uuid), HttpStatus.OK);
    }


}
