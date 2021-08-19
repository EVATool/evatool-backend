package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.StakeholderController;
import com.evatool.application.dto.StakeholderDto;
import com.evatool.application.service.impl.StakeholderServiceImpl;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Stakeholder;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Stakeholder API-Endpoint")
@RestController
@CrossOrigin
public class StakeholderControllerImpl extends CrudControllerImpl<Stakeholder, StakeholderDto> implements StakeholderController {

    private static final Logger logger = LoggerFactory.getLogger(StakeholderControllerImpl.class);

    @Getter
    private final StakeholderServiceImpl service;

    protected StakeholderControllerImpl(StakeholderServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.STAKEHOLDERS_LEVELS)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<StakeholderLevel>> findAllStakeholderLevels() {
        return new ResponseEntity<>(service.findAllStakeholderLevels(), HttpStatus.OK);
    }

    @Override
    @GetMapping(UriUtil.STAKEHOLDERS_PRIORITIES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<StakeholderPriority>> findAllStakeholderPriorities() {
        return new ResponseEntity<>(service.findAllStakeholderPriorities(), HttpStatus.OK);
    }

    @Override
    @GetMapping(UriUtil.STAKEHOLDERS)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<StakeholderDto>> findAllByAnalysisId(UUID analysisId) {
        return StakeholderController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.STAKEHOLDERS_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<StakeholderDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.STAKEHOLDERS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<StakeholderDto> create(StakeholderDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.STAKEHOLDERS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<StakeholderDto> update(StakeholderDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.STAKEHOLDERS_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
