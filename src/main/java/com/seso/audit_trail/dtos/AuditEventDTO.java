package com.seso.audit_trail.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditEventDTO {
    private String action;
    private String service;
    private String userId;
    private String summary;
    private String data;
    private LocalDateTime createdAt;
}
