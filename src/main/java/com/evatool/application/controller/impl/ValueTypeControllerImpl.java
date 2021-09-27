package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ValueTypeController;
import com.evatool.application.dto.ValueTypeDto;
import com.evatool.application.service.impl.ValueTypeServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.ValueType;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Value Type API-Endpoint")
@RestController
public class ValueTypeControllerImpl extends CrudControllerImpl<ValueType, ValueTypeDto> implements ValueTypeController {

    private static final Logger logger = LoggerFactory.getLogger(ValueTypeControllerImpl.class);

    @Getter
    private final ValueTypeServiceImpl service;

    public ValueTypeControllerImpl(ValueTypeServiceImpl service) {
        super(service);
        logger.trace("Constructor");
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.VALUE_TYPES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<ValueTypeDto>> findAllByAnalysisId(UUID analysisId) {
        return ValueTypeController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.VALUE_TYPES_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<ValueTypeDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.VALUE_TYPES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<ValueTypeDto> create(ValueTypeDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.VALUE_TYPES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<ValueTypeDto> update(ValueTypeDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.VALUE_TYPES_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
