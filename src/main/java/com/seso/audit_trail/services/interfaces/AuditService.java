package com.seso.audit_trail.services.interfaces;

import com.seso.audit_trail.dtos.AuditEventDTO;
import com.seso.audit_trail.dtos.AuditEventSearchDTO;
import com.seso.audit_trail.dtos.ResponseData;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuditService {
    void log(AuditEventDTO auditEventDTO);
    ResponseData search(AuditEventSearchDTO auditEventSearchDTO, Pageable pageable);

}
