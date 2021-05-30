package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.CrudController;
import com.evatool.application.dto.SuperDto;
import com.evatool.application.service.impl.CrudServiceImpl;
import com.evatool.domain.entity.SuperEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public abstract class CrudControllerImpl<S extends SuperEntity, T extends SuperDto> implements CrudController<T> {

    private static final Logger logger = LoggerFactory.getLogger(CrudControllerImpl.class);

    protected final CrudServiceImpl<S, T> superService;

    protected CrudControllerImpl(CrudServiceImpl<S, T> superService) {
        this.superService = superService;
    }

    @Override
    public ResponseEntity<EntityModel<T>> findById(UUID id) {
        logger.debug("Find By Id");
        var dtoFound = superService.findById(id);
        return new ResponseEntity<>(withLinks(dtoFound), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityModel<T>> create(T dto) {
        logger.debug("Create");
        var dtoInserted = superService.create(dto);
        return new ResponseEntity<>(withLinks(dtoInserted), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<EntityModel<T>> update(T dto) {
        logger.debug("Update");
        var dtoUpdated = superService.update(dto);
        return new ResponseEntity<>(withLinks(dtoUpdated), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        logger.debug("Delete");
        superService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public EntityModel<T> withLinks(T dto) {
        logger.debug("With Links");
        var entityModel = EntityModel.of(dto);
        entityModel.add(linkTo(methodOn(getClass()).findById(dto.getId())).withSelfRel());
        return entityModel;
    }

    public Iterable<EntityModel<T>> withLinks(Iterable<T> dtoList) {
        logger.debug("With Links");
        var entityModelList = new ArrayList<EntityModel<T>>();
        dtoList.forEach(dto -> entityModelList.add(withLinks(dto)));
        return entityModelList;
    }
}
