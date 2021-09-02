package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ImpactController;
import com.evatool.application.dto.ImpactDto;
import com.evatool.application.service.impl.ImpactServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Impact;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Impact API-Endpoint")
@RestController
@CrossOrigin
public class ImpactControllerImpl extends CrudControllerImpl<Impact, ImpactDto> implements ImpactController {

    private static final Logger logger = LoggerFactory.getLogger(ImpactControllerImpl.class);

    @Getter
    private final ImpactServiceImpl service;

    public ImpactControllerImpl(ImpactServiceImpl service) {
        super(service);
        logger.trace("Constructor");
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.IMPACTS)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<ImpactDto>> findAllByAnalysisId(UUID analysisId) {
        return ImpactController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.IMPACTS_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<ImpactDto> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.IMPACTS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<ImpactDto> create(ImpactDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.IMPACTS)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<ImpactDto> update(ImpactDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.IMPACTS_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
