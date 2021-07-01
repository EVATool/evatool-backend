package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.RequirementDeltaController;
import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.application.service.impl.RequirementDeltaServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.RequirementDelta;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = "Requirement-Delta API-Endpoint")
@RestController
@CrossOrigin
public class RequirementDeltaControllerImpl extends CrudControllerImpl<RequirementDelta, RequirementDeltaDto> implements RequirementDeltaController {

    private static final Logger logger = LoggerFactory.getLogger(RequirementDeltaControllerImpl.class);

    @Getter
    private final RequirementDeltaServiceImpl service;

    protected RequirementDeltaControllerImpl(RequirementDeltaServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(value = UriUtil.REQUIREMENTS_DELTA, params = {"!impactId", "!requirementId"})
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Iterable<EntityModel<RequirementDeltaDto>>> findAllByAnalysisId(UUID analysisId) {
        return RequirementDeltaController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.REQUIREMENTS_DELTA_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<EntityModel<RequirementDeltaDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.REQUIREMENTS_DELTA)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<EntityModel<RequirementDeltaDto>> create(RequirementDeltaDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.REQUIREMENTS_DELTA)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<EntityModel<RequirementDeltaDto>> update(RequirementDeltaDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.REQUIREMENTS_DELTA_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }

    @Override
    public EntityModel<RequirementDeltaDto> withLinks(RequirementDeltaDto dto) {
        var entityModel = super.withLinks(dto);
        entityModel.add(linkTo(methodOn(ImpactControllerImpl.class).findById(dto.getImpactId())).withRel(UriUtil.IMPACTS_REL));
        entityModel.add(linkTo(methodOn(RequirementControllerImpl.class).findById(dto.getRequirementId())).withRel(UriUtil.REQUIREMENTS_REL));
        return entityModel;
    }
}
