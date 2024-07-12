package com.seso.audit_trail.dtos;

import lombok.Data;

@Data
public class AuditEventSearchDTO {
    private String action;
    private String service;
    private String userId;
    private String summary;
    private String startDate;
    private String endDate;
}
