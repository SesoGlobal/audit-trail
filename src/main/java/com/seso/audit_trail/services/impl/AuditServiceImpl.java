package com.seso.audit_trail.services.impl;

import com.seso.audit_trail.dtos.AuditEventDTO;
import com.seso.audit_trail.dtos.AuditEventSearchDTO;
import com.seso.audit_trail.dtos.ResponseData;
import com.seso.audit_trail.models.AuditEvent;
import com.seso.audit_trail.models.AuditEventSearch;
import com.seso.audit_trail.repositories.AuditEventRepository;
import com.seso.audit_trail.services.interfaces.AuditService;
import com.seso.audit_trail.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {
    private static final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);
    private final AuditEventRepository auditEventRepository;
    private  final EncryptionUtil encryptionUtil;

    @Autowired
    public AuditServiceImpl(AuditEventRepository auditEventRepository, EncryptionUtil encryptionUtil) {
        this.auditEventRepository = auditEventRepository;
        this.encryptionUtil = encryptionUtil;
    }

    @Override
    public void log(AuditEventDTO auditEventDTO) {
        AuditEvent auditEvent = new AuditEvent();
        auditEvent.setAction(auditEventDTO.getAction());
        auditEvent.setService(auditEventDTO.getService());
        auditEvent.setData(auditEventDTO.getData());
        auditEvent.setUserId(auditEventDTO.getUserId());
        auditEvent.setSummary(auditEventDTO.getSummary());
        auditEvent.setCreatedAt(LocalDateTime.now());
        auditEventRepository.saveAuditEvent(auditEvent);
        logger.info("Audit event logged");
    }

    @Override
    public ResponseData search(AuditEventSearchDTO auditEventSearchDTO, Pageable pageable) {
        try{
            LocalDateTime startDateTime = LocalDateTime.parse(auditEventSearchDTO.getStartDate());
            LocalDateTime endDateTime = LocalDateTime.parse(auditEventSearchDTO.getEndDate());
            AuditEventSearch auditEventSearch = new AuditEventSearch(
                    auditEventSearchDTO.getAction(),
                    auditEventSearchDTO.getService(),auditEventSearchDTO.getUserId(),auditEventSearchDTO.getSummary(), startDateTime, endDateTime);

            Page<AuditEvent> auditEventPage = auditEventRepository
                    .searchAuditEvents(auditEventSearch,
                            pageable);

            HashMap<String, Object> data = getAuditEvents(auditEventPage);
            return new ResponseData(data, "Audit events retrieved", HttpStatus.OK);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format", e);
            return new ResponseData(null, "Invalid date format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred", e);
            return new ResponseData(null, "An error occured", HttpStatus.EXPECTATION_FAILED);
        }
    }

    HashMap<String, Object> getAuditEvents(Page<AuditEvent> auditEventPage) {
        List<AuditEventDTO> auditEvents = auditEventPage.getContent()
                .stream().map(auditEvent -> {
                    AuditEventDTO auditEventDTO = new AuditEventDTO();
                    auditEventDTO.setAction(auditEvent.getAction());
                    auditEventDTO.setService(auditEvent.getService());
                   // auditEventDTO.setData( encryptionUtil.decrypt(auditEvent.getData()));
                    auditEventDTO.setUserId(auditEvent.getUserId());
                    auditEventDTO.setSummary(auditEvent.getSummary());
                    auditEventDTO.setCreatedAt(auditEvent.getCreatedAt());
                    if(auditEvent.getData() == null || auditEvent.getData().isEmpty()){
                        auditEventDTO.setData("No data");
                    }else{
                        auditEventDTO.setData(encryptionUtil.decrypt(auditEvent.getData()));
                    }
                    return auditEventDTO;
                }).collect(Collectors.toList());
        Long totalElements = auditEventPage.getTotalElements();
        HashMap<String, Object> data = new HashMap<>();
        data.put("auditEvents", auditEvents);
        data.put("totalElements", totalElements);
        return data;
    }
}
