package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.AnalysisController;
import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.service.impl.AnalysisServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Analysis;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Analysis API-Endpoint")
@RestController
public class AnalysisControllerImpl extends CrudControllerImpl<Analysis, AnalysisDto> implements AnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisControllerImpl.class);

    private final AnalysisServiceImpl service;

    public AnalysisControllerImpl(AnalysisServiceImpl service) {
        super(service);
        logger.trace("Constructor");
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.ANALYSES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<AnalysisDto>> findAll() {
        var dtoListFound = service.findAll();
        return new ResponseEntity<>(dtoListFound, HttpStatus.OK);
    }

    @Override
    @PostMapping(UriUtil.ANALYSES_DEEP_COPY)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<AnalysisDto> deepCopy(UUID templateAnalysisId, AnalysisDto analysisDto) {
        logger.trace("Deep Copy");
        return new ResponseEntity<>(service.deepCopy(templateAnalysisId, analysisDto), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(UriUtil.ANALYSES_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<AnalysisDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.ANALYSES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<AnalysisDto> create(AnalysisDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.ANALYSES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<AnalysisDto> update(AnalysisDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.ANALYSES_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
