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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<Iterable<EntityModel<VariantDto>>> findAllByAnalysisId(UUID analysisId) {
        return VariantController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.VARIANTS_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN_OR_USER)
    public ResponseEntity<EntityModel<VariantDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.VARIANTS)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<VariantDto>> create(VariantDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.VARIANTS)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<EntityModel<VariantDto>> update(VariantDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.VARIANTS_ID)
    @PreAuthorize(AuthUtil.BY_ADMIN)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }

    @Override
    public EntityModel<VariantDto> withLinks(VariantDto dto) {
        var entityModel = super.withLinks(dto);
        entityModel.add(linkTo(methodOn(AnalysisControllerImpl.class).findById(dto.getAnalysisId())).withRel(UriUtil.ANALYSIS_REL));
        entityModel.add(Link.of(UriUtil.VARIANTS_ID).withRel(UriUtil.VARIANTS_REL));
        return entityModel;
    }
}
