package com.evatool.application.controller;

import com.evatool.application.controller.impl.*;
import com.evatool.application.dto.*;
import com.evatool.domain.entity.SuperEntity;
import com.evatool.domain.repository.DataTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
abstract class CrudControllerTest<S extends SuperEntity, T extends SuperDto> extends DataTest<S, T> {

    @Test
    void testFindById() {
        // given
        var dto = getPersistedDto();

        // when
        var response = getController().findById(dto.getId());
        var dtoFound = getDtoFromResponseEntity(response);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoFound).isEqualTo(dto);
    }

    @Test
    void testCreate() {
        // given
        var dto = getFloatingDto();

        // when
        var response = getController().create(dto);
        var dtoCreated = getDtoFromResponseEntity(response);
        var dtoFound = getService().findById(dtoCreated.getId());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(dtoFound).isEqualTo(dtoCreated);
    }

    @Test
    void testUpdate() {
        // given
        var dto = getPersistedDto();

        // when
        changeDto(dto);
        var response = getController().update(dto);
        var dtoUpdated = getDtoFromResponseEntity(response);
        var dtoFound = getService().findById(dto.getId());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoFound).isEqualTo(dtoUpdated);
    }

    @Test
    void testDeleteById() {
        // given
        var dto = getPersistedDto();

        // when
        var response = getController().deleteById(dto.getId());
        var dtoListFound = getService().findAll();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoListFound).isEmpty();
    }

    public CrudControllerImpl getController() {
        var type = getDtoClass();
        if (type == AnalysisDto.class) {
            return analysisController;
        } else if (type == ImpactDto.class) {
            return impactController;
        } else if (type == RequirementDto.class) {
            return requirementController;
        } else if (type == RequirementDeltaDto.class) {
            return requirementDeltaController;
        } else if (type == StakeholderDto.class) {
            return stakeholderController;
        } else if (type == UserDto.class) {
            return userController;
        } else if (type == ValueDto.class) {
            return valueController;
        } else if (type == VariantDto.class) {
            return variantController;
        } else {
            throw new IllegalArgumentException("No controller found for type " + type.getSimpleName());
        }
    }

    @Autowired
    private AnalysisControllerImpl analysisController;

    @Autowired
    private ImpactControllerImpl impactController;

    @Autowired
    private RequirementControllerImpl requirementController;

    @Autowired
    private RequirementDeltaControllerImpl requirementDeltaController;

    @Autowired
    private StakeholderControllerImpl stakeholderController;

    @Autowired
    private UserControllerImpl userController;

    @Autowired
    private ValueControllerImpl valueController;

    @Autowired
    private VariantControllerImpl variantController;

    public T getDtoFromResponseEntity(ResponseEntity<EntityModel<T>> response) {
        return getDtoFromEntityModel(Objects.requireNonNull(response.getBody()));
    }

    public T getDtoFromEntityModel(EntityModel<T> entityModel) {
        return entityModel.getContent();
    }

    public Iterable<T> getDtoFromResponseList(ResponseEntity<Iterable<EntityModel<T>>> response) {
        var dtoList = new ArrayList<T>();
        Objects.requireNonNull(response.getBody()).forEach(dto -> dtoList.add(getDtoFromEntityModel(dto)));
        return dtoList;
    }
}
