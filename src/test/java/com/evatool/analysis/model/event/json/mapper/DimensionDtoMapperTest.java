package com.evatool.analysis.model.event.json.mapper;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.analysis.application.dto.ValueDtoMapper.fromDto;
import static com.evatool.analysis.application.dto.ValueDtoMapper.toDto;
import static org.assertj.core.api.Assertions.assertThat;
import static com.evatool.analysis.common.TestDataGenerator.*;


class DimensionDtoMapperTest {

    @Test
    void testToDto_RecreatedValue_EqualsVlue() {
        // given
        var value = createDummyValue();
        value.setId(UUID.randomUUID());

        // when
        var valueDto = toDto(value);
        var recreatedValue = fromDto(valueDto);

        // then
        assertThat(value).isEqualTo(recreatedValue);
    }

    @Test
    void testFromDto_RecreatedValueDto_EqualsValueDto() {
        // given
        var valueDto = createDummyValueDto();
        valueDto.setId(UUID.randomUUID());

        // when
        var value = fromDto(valueDto);
        var recreatedValueDto = toDto(value);

        // then
        assertThat(valueDto).isEqualTo(recreatedValueDto);
    }
}
