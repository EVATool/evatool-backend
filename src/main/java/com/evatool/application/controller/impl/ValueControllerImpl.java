package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ValueController;
import com.evatool.application.dto.ValueDto;
import com.evatool.application.service.impl.ValueServiceImpl;
import com.evatool.common.enums.ValueType;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Value;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = "Value API-Endpoint")
@RestController
@CrossOrigin
public class ValueControllerImpl extends CrudControllerImpl<Value, ValueDto> implements ValueController {

    private static final Logger logger = LoggerFactory.getLogger(ValueControllerImpl.class);

    @Getter
    private final ValueServiceImpl service;

    public ValueControllerImpl(ValueServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping(UriUtil.VALUES_TYPES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Iterable<ValueType>> findAllValuesTypes() {
        return new ResponseEntity<>(service.findAllValueTypes(), HttpStatus.OK);
    }

    @Override
    @GetMapping(UriUtil.VALUES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Iterable<EntityModel<ValueDto>>> findAllByAnalysisId(UUID analysisId) {
        return ValueController.super.findAllByAnalysisId(analysisId);
    }

    @Override
    @GetMapping(UriUtil.VALUES_ID)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<EntityModel<ValueDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.VALUES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<EntityModel<ValueDto>> create(ValueDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.VALUES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<EntityModel<ValueDto>> update(ValueDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.VALUES_ID)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }

    @Override
    public EntityModel<ValueDto> withLinks(ValueDto dto) {
        var entityModel = super.withLinks(dto);
        entityModel.add(linkTo(methodOn(AnalysisControllerImpl.class).findById(dto.getAnalysisId())).withRel(UriUtil.ANALYSIS_REL));
        return entityModel;
    }
}
