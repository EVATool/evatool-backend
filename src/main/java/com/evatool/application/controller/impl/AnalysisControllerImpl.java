package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.AnalysisController;
import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.service.impl.AnalysisServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Analysis;
import io.swagger.annotations.Api;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Api(tags = "Analysis API-Endpoint")
@RestController
@CrossOrigin
public class AnalysisControllerImpl extends CrudControllerImpl<Analysis, AnalysisDto> implements AnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisControllerImpl.class);

    private final AnalysisServiceImpl service;

    public AnalysisControllerImpl(AnalysisServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.ANALYSES)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<Iterable<EntityModel<AnalysisDto>>> findAll() {
        var dtoListFound = service.findAll();
        return new ResponseEntity<>(withLinks(dtoListFound), HttpStatus.OK);
    }

    @Override
    @PostMapping(UriUtil.ANALYSES_DEEP_COPY)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<AnalysisDto>> deepCopy(UUID templateAnalysisId, AnalysisDto analysisDto) {
        logger.debug("Deep Copy");
        return new ResponseEntity<>(withLinks(service.deepCopy(templateAnalysisId, analysisDto)), HttpStatus.CREATED);
    }

    @Override
    @GetMapping(UriUtil.ANALYSES_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<EntityModel<AnalysisDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.ANALYSES)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<AnalysisDto>> create(AnalysisDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.ANALYSES)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<AnalysisDto>> update(AnalysisDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.ANALYSES_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
