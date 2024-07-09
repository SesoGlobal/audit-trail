package com.seso.audit_trail.repositories;

import com.seso.audit_trail.dtos.AuditEventSearchDTO;
import com.seso.audit_trail.models.AuditEvent;
import com.seso.audit_trail.models.AuditEventSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AuditEventRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveAuditEvent(AuditEvent auditEvent) {
        mongoTemplate.save(auditEvent);
    }
    public Page<AuditEvent> searchAuditEvents(AuditEventSearch search, Pageable pageable) {
        Query query = new Query();

        if (search.getUserId() != null && !search.getUserId().isEmpty()) {
            query.addCriteria(Criteria.where("userId").is(search.getUserId()));
        }
        if (search.getService() != null && !search.getService().isEmpty()) {
            query.addCriteria(Criteria.where("service").is(search.getService()));
        }

        query.addCriteria(Criteria.where("createdAt").gte(search.getStartDate()).lte(search.getEndDate()));


        long count = mongoTemplate.count(query, AuditEvent.class);
        List<AuditEvent> auditEvents = mongoTemplate.find(query.with(pageable), AuditEvent.class);

        return new PageImpl<>(auditEvents, pageable, count);
    }
    }
