package com.seso.audit_trail.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class AuditEventSearch {
    private String service;
    private String userId;
    private String summary;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
