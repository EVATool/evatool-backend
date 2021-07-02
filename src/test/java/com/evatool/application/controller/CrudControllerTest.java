package com.evatool.application.controller;

import com.evatool.application.dto.*;
import com.evatool.common.util.ProfileUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.SuperEntity;
import com.evatool.domain.repository.DataTest;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
abstract class CrudControllerTest<S extends SuperEntity, T extends SuperDto> extends DataTest<S, T> {

    @Test
    void testFindById() {
        // given
        var dto = getPersistedDto();

        // when
        var response = rest.getForEntity(getUri() + "/" + dto.getId(), getDtoClass());
        var dtoFound = (T) response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoFound).isEqualTo(dto);
    }

    @Test
    void testCreate() {
        // given
        var dto = getFloatingDto();

        // when
        var httpEntity = new HttpEntity<>(dto);
        var response = rest.postForEntity(getUri(), httpEntity, getDtoClass());
        var dtoCreated = (T) response.getBody();
        var dtoFound = (T) getService().findById(dtoCreated.getId());

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
        var httpEntity = new HttpEntity<>(dto);
        var response = rest.exchange(getUri(), HttpMethod.PUT, httpEntity, getDtoClass());
        var dtoUpdated = (T) response.getBody();
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
        var httpEntity = new HttpEntity<>(dto);
        var response = rest.exchange(getUri() + "/" + dto.getId(), HttpMethod.DELETE, httpEntity, Void.class);
        var dtoListFound = getService().findAll();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoListFound).isEmpty();
    }

    @Test
    void restLevel3() {
        // given
        var dto = getPersistedDto();

        // when
        var response = rest.getForEntity(getUri() + "/" + dto.getId(), EntityModel.class);
        var entityModel = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entityModel.getLink("self")).isPresent().contains(Link.of("http://localhost:8080" + getUri() + "/" + dto.getId(), "self"));
    }

    public String getUri() {
        Class<?> type = getDtoClass();
        if (type == AnalysisDto.class) {
            return UriUtil.ANALYSES;
        } else if (type == ImpactDto.class) {
            return UriUtil.IMPACTS;
        } else if (type == RequirementDto.class) {
            return UriUtil.REQUIREMENTS;
        } else if (type == RequirementDeltaDto.class) {
            return UriUtil.REQUIREMENTS_DELTA;
        } else if (type == StakeholderDto.class) {
            return UriUtil.STAKEHOLDERS;
        } else if (type == UserDto.class) {
            return UriUtil.USERS;
        } else if (type == ValueDto.class) {
            return UriUtil.VALUES;
        } else if (type == VariantDto.class) {
            return UriUtil.VARIANTS;
        } else {
            throw new IllegalArgumentException("No controller found for type " + type.getSimpleName());
        }
    }

    public Class<T[]> getDtoClassArray() {
        var type = getDtoClass();
        return (Class<T[]>) Array.newInstance(type, 0).getClass();
    }

    @Autowired
    @Getter
    public TestRestTemplate rest;

}
