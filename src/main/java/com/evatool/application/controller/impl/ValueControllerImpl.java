package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ValueController;
import com.evatool.application.dto.ValueDto;
import com.evatool.application.service.impl.ValueServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Value;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Value API-Endpoint")
@RestController
public class ValueControllerImpl extends CrudControllerImpl<Value, ValueDto> implements ValueController {

    private static final Logger logger = LoggerFactory.getLogger(ValueControllerImpl.class);

    @Getter
    private final ValueServiceImpl service;

    public ValueControllerImpl(ValueServiceImpl service) {
        super(service);
        logger.trace("Constructor");
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.VALUES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<ValueDto>> findAllByAnalysisId(UUID analysisId) {
        return ValueController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.VALUES_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<ValueDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.VALUES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<ValueDto> create(ValueDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.VALUES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<ValueDto> update(ValueDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.VALUES_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
