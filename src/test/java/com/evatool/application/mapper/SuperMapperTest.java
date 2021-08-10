package com.evatool.application.mapper;

import com.evatool.application.dto.SuperDto;
import com.evatool.domain.entity.SuperEntity;
import com.evatool.domain.repository.DataTest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

abstract class SuperMapperTest<S extends SuperEntity, T extends SuperDto, U extends SuperMapper> extends DataTest<S, T> {

    @Test
    void testToAndFromDto_RecreateFloatingEntity() {
        // given
        var entity = getFloatingEntity();

        // when
        var dto = getMapper().toDto(entity);
        var recreatedEntity = getMapper().fromDto(dto);

        // then
        assertThat(entity).isEqualTo(recreatedEntity);
    }

    @Test
    void testToAndFromDto_RecreatePersistedEntity() {
        // given
        var entity = getPersistedEntity();

        // when
        var dto = getMapper().toDto(entity);
        var recreatedEntity = getMapper().fromDto(dto);

        // then
        assertThat(entity).isEqualTo(recreatedEntity);
    }

    @SneakyThrows
    @Test
    void testToAndFromJson_RecreatePersistedDto() {
        // given
        var dto = getPersistedDto();

        // when
        var json = getMapper().toJson(dto);
        var recreatedDto = getMapper().fromJson(json);

        // then
        for (var field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            var expected = field.get(dto);
            var actual = field.get(recreatedDto);
            if (!isJsonIgnored(field)) {
                System.out.println(actual + ", " + expected);
                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    private boolean isJsonIgnored(Field field) {
        for (var annotation : field.getDeclaredAnnotations()) {
            if (annotation.annotationType().equals(JsonIgnore.class)) {
                return true;
            }
        }
        return false;
    }
}
