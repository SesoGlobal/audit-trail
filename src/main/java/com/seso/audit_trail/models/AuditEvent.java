package com.seso.audit_trail.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("audit_events")
@Data
public class AuditEvent {
    @Id
    private String id;
    private String action;
    private String service;
    String userId;
    private String data;
    @CreatedDate
    private LocalDateTime createdAt;
}
