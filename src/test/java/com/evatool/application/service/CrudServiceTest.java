package com.evatool.application.service;

import com.evatool.application.dto.SuperDto;
import com.evatool.common.exception.functional.http404.EntityNotFoundException;
import com.evatool.common.exception.prevent.http422.PropertyCannotBeNullException;
import com.evatool.common.exception.prevent.http422.PropertyMustBeNullException;
import com.evatool.domain.entity.SuperEntity;
import com.evatool.domain.repository.DataTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
abstract class CrudServiceTest<S extends SuperEntity, T extends SuperDto> extends DataTest<S, T> {

    @Test
    void testFindById() {
        // given
        var dto = getPersistedDto();

        // when
        var dtoFound = getService().findById(dto.getId());

        // then
        assertThat(dtoFound).isEqualTo(dto);
    }

    @Test
    void testFindAll() {
        // given
        var dto1 = getPersistedDto();
        var dto2 = getPersistedDto();

        // when
        var dtoListFound = getService().findAll();

        // then
        assertThat(dtoListFound).isEqualTo(Arrays.asList(dto1, dto2));
    }

    @Test
    void testCreate() {
        // given
        var dto = getFloatingDto();

        // when
        var dtoCreated = getService().create(dto);
        var dtoFound = getService().findById(dtoCreated.getId());

        // then
        assertThat(dtoFound).isEqualTo(dtoCreated);
    }

    @Test
    void testUpdate() {
        // given
        var dto = getPersistedDto();

        // when
        changeDto(dto);
        var dtoUpdated = getService().update(dto);
        var dtoFound = getService().findById(dto.getId());

        // then
        assertThat(dtoFound).isEqualTo(dtoUpdated);
    }

    @Test
    void testDeleteById() {
        // given
        var dto = getPersistedDto();

        // when
        getService().deleteById(dto.getId());
        var dtoListFound = getService().findAll();

        // then
        assertThat(dtoListFound).isEmpty();
    }

    @Test
    void testDeleteAll() {
        // given
        var dto1 = getPersistedDto();
        var dto2 = getPersistedDto();

        // when
        getService().deleteAll();
        var dtoListFound = getService().findAll();

        // then
        assertThat(dtoListFound).isEmpty();
    }

    @Test
    void testFindById_IdEqualsNull_Throws() {
        // given

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(PropertyCannotBeNullException.class).isThrownBy(() -> service.findById(null));
    }

    @Test
    void testFindById_EntityWithIdNotFound_Throws() {
        // given
        var id = UUID.randomUUID();

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> service.findById(id));
    }

    @Test
    void testCreate_IdNotNull_Throws() {
        // given
        var dto = getPersistedDto();

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(PropertyMustBeNullException.class).isThrownBy(() -> service.create(dto));
    }

    @Test
    void testUpdate_IdEqualsNull_Throws() {
        // given
        var dto = getFloatingDto();

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(PropertyCannotBeNullException.class).isThrownBy(() -> service.update(dto));
    }

    @Test
    void testUpdate_EntityWithIdNotFound_Throws() {
        // given
        var dto = getFloatingDto();
        dto.setId(UUID.randomUUID());

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> service.update(dto));
    }

    @Test
    void testDelete_IdEqualsNull_Throws() {
        // given

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(PropertyCannotBeNullException.class).isThrownBy(() -> service.deleteById(null));
    }

    @Test
    void testDelete_EntityWithIdNotFound_Throws() {
        // given
        var id = UUID.randomUUID();

        // when
        var service = getService();

        // then
        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> service.deleteById(id));
    }
}
