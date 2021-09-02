package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.RequirementController;
import com.evatool.application.dto.RequirementDto;
import com.evatool.application.service.impl.RequirementServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Requirement;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Requirement API-Endpoint")
@RestController
@CrossOrigin
public class RequirementControllerImpl extends CrudControllerImpl<Requirement, RequirementDto> implements RequirementController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementControllerImpl.class);

    @Getter
    private final RequirementServiceImpl service;

    protected RequirementControllerImpl(RequirementServiceImpl service) {
        super(service);
        logger.trace("Constructor");
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.REQUIREMENTS)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<RequirementDto>> findAllByAnalysisId(UUID analysisId) {
        return RequirementController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.REQUIREMENTS_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<RequirementDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.REQUIREMENTS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<RequirementDto> create(RequirementDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.REQUIREMENTS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<RequirementDto> update(RequirementDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.REQUIREMENTS_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
