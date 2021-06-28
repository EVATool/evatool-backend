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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = "Requirement API-Endpoint")
@RestController
@CrossOrigin
public class RequirementControllerImpl extends CrudControllerImpl<Requirement, RequirementDto> implements RequirementController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementControllerImpl.class);

    @Getter
    private final RequirementServiceImpl service;

    protected RequirementControllerImpl(RequirementServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.REQUIREMENTS)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<Iterable<EntityModel<RequirementDto>>> findAllByAnalysisId(UUID analysisId) {
        return RequirementController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.REQUIREMENTS_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<EntityModel<RequirementDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.REQUIREMENTS)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<RequirementDto>> create(RequirementDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.REQUIREMENTS)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<RequirementDto>> update(RequirementDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.REQUIREMENTS_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }

    @Override
    public EntityModel<RequirementDto> withLinks(RequirementDto dto) {
        var entityModel = super.withLinks(dto);
        entityModel.add(linkTo(methodOn(AnalysisControllerImpl.class).findById(dto.getAnalysisId())).withRel(UriUtil.ANALYSIS_REL));
        entityModel.add(Link.of(UriUtil.VARIANTS_ID).withRel(UriUtil.VARIANTS_REL));
        return entityModel;
    }
}
