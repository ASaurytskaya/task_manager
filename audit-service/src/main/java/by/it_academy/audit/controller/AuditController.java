package by.it_academy.audit.controller;

import by.it_academy.audit.core.dto.AuditCreate;
import by.it_academy.audit.service.api.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/audit")
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
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

        return new ResponseEntity<>(auditService.getPage(page, size), HttpStatus.OK);
    }


    @GetMapping(value = "/{uuid}")
    public ResponseEntity<?> getAudit(@PathVariable UUID uuid) {

        return new ResponseEntity<>(auditService.getAudit(uuid), HttpStatus.OK);
    }


}
