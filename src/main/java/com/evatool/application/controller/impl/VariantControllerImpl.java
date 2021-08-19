package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.VariantController;
import com.evatool.application.dto.VariantDto;
import com.evatool.application.service.impl.VariantServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Variant;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Variant API-Endpoint")
@RestController
@CrossOrigin
public class VariantControllerImpl extends CrudControllerImpl<Variant, VariantDto> implements VariantController {

    private static final Logger logger = LoggerFactory.getLogger(VariantControllerImpl.class);

    @Getter
    private final VariantServiceImpl service;

    public VariantControllerImpl(VariantServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.VARIANTS)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<VariantDto>> findAllByAnalysisId(UUID analysisId) {
        return VariantController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.VARIANTS_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<VariantDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.VARIANTS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<VariantDto> create(VariantDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.VARIANTS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<VariantDto> update(VariantDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.VARIANTS_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
