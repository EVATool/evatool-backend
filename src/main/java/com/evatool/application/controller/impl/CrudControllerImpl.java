package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.CrudController;
import com.evatool.application.dto.SuperDto;
import com.evatool.application.service.impl.CrudServiceImpl;
import com.evatool.domain.entity.SuperEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public abstract class CrudControllerImpl<S extends SuperEntity, T extends SuperDto> implements CrudController<T> {

    private static final Logger logger = LoggerFactory.getLogger(CrudControllerImpl.class);

    protected final CrudServiceImpl<S, T> superService;

    protected CrudControllerImpl(CrudServiceImpl<S, T> superService) {
        this.superService = superService;
    }

    @Override
    public ResponseEntity<T> findById(UUID id) {
        logger.debug("Find By Id");
        var dtoFound = superService.findById(id);
        return new ResponseEntity<>(dtoFound, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<T> create(T dto) {
        logger.debug("Create");
        var dtoInserted = superService.create(dto);
        return new ResponseEntity<>(dtoInserted, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<T> update(T dto) {
        logger.debug("Update");
        var dtoUpdated = superService.update(dto);
        return new ResponseEntity<>(dtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteById(UUID id) {
        logger.debug("Delete");
        superService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
