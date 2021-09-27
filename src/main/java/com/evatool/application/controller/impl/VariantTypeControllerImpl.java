package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.VariantTypeController;
import com.evatool.application.dto.VariantTypeDto;
import com.evatool.application.service.impl.VariantTypeServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.VariantType;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Variant Type API-Endpoint")
@RestController
public class VariantTypeControllerImpl extends CrudControllerImpl<VariantType, VariantTypeDto> implements VariantTypeController {

    private static final Logger logger = LoggerFactory.getLogger(VariantTypeControllerImpl.class);

    @Getter
    private final VariantTypeServiceImpl service;

    public VariantTypeControllerImpl(VariantTypeServiceImpl service) {
        super(service);
        logger.trace("Constructor");
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.VARIANT_TYPES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<VariantTypeDto>> findAllByAnalysisId(UUID analysisId) {
        return VariantTypeController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.VARIANT_TYPES_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<VariantTypeDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.VARIANT_TYPES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<VariantTypeDto> create(VariantTypeDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.VARIANT_TYPES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<VariantTypeDto> update(VariantTypeDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.VARIANT_TYPES_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
