package by.it_academy.audit.controller;

import by.it_academy.audit.core.dto.Audit;
import by.it_academy.audit.core.dto.ReportParamAudit;
import by.it_academy.audit.service.HelperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/audit/report")
public class ReportHelperController {
    private final HelperService helperService;

    public ReportHelperController(HelperService helperService) {
        this.helperService = helperService;
    }

    @PostMapping
    public ResponseEntity<?> getList(@RequestBody ReportParamAudit param, @RequestHeader("Authorization") String token) {
        List<Audit> list = helperService.getFilteredList(param);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }





}
