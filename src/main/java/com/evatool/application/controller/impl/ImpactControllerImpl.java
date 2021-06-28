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
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = "Impact API-Endpoint")
@RestController
@CrossOrigin
public class ImpactControllerImpl extends CrudControllerImpl<Impact, ImpactDto> implements ImpactController {

    private static final Logger logger = LoggerFactory.getLogger(ImpactControllerImpl.class);

    @Getter
    private final ImpactServiceImpl service;

    public ImpactControllerImpl(ImpactServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.IMPACTS)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<Iterable<EntityModel<ImpactDto>>> findAllByAnalysisId(UUID analysisId) {
        return ImpactController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.IMPACTS_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<EntityModel<ImpactDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.IMPACTS)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<ImpactDto>> create(ImpactDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.IMPACTS)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<ImpactDto>> update(ImpactDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.IMPACTS_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }

    @Override
    public EntityModel<ImpactDto> withLinks(ImpactDto dto) {
        var entityModel = super.withLinks(dto);
        entityModel.add(linkTo(methodOn(ValueControllerImpl.class).findById(dto.getValueId())).withRel(UriUtil.VALUES_REL));
        entityModel.add(linkTo(methodOn(StakeholderControllerImpl.class).findById(dto.getStakeholderId())).withRel(UriUtil.STAKEHOLDERS_REL));
        entityModel.add(linkTo(methodOn(AnalysisControllerImpl.class).findById(dto.getAnalysisId())).withRel(UriUtil.ANALYSIS_REL));
        return entityModel;
    }
}
