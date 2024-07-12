package com.seso.audit_trail.controllers;

import com.seso.audit_trail.dtos.AuditEventDTO;
import com.seso.audit_trail.dtos.AuditEventSearchDTO;
import com.seso.audit_trail.dtos.ResponseData;
import com.seso.audit_trail.services.interfaces.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuditController {
    private final AuditService auditService;

    @Autowired
    AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping("/audit")
    @CrossOrigin(origins = "http://localhost:3000")
    public void logAuditEvent(@RequestBody AuditEventDTO auditEventDTO) {
        System.out.println("Received audit event");
        auditService.log(auditEventDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData> getAuditEventsByService(AuditEventSearchDTO auditEventSearchDTO, Pageable pageable) {
        ResponseData responseData = auditService.search(auditEventSearchDTO, pageable);
        return new ResponseEntity<ResponseData>(responseData, responseData.getHttpStatus());
    }


}
