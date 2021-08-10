package com.evatool.application.mapper;

import com.evatool.application.dto.SuperDto;
import com.evatool.domain.entity.SuperEntity;
import com.evatool.domain.repository.DataTest;
import org.junit.jupiter.api.Test;

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

    @Test
    void testToAndFromJson_RecreatePersistedEntity() {
        // given
        var dto = getPersistedDto();

        // when
        var json = getMapper().toJson(dto);
        var recreatedDto = getMapper().fromJson(json);

        // then
        System.out.println(dto);
        System.out.println(json);
        System.out.println(recreatedDto);

        assertThat(dto).isEqualTo(recreatedDto);
    }
}
