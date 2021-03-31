package com.evatool.impact.application.dto.mapper;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper.fromDto;
import static com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static com.evatool.impact.common.TestDataGenerator.createDummyValueDto;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactValueDtoMapperTest {

    @Test
    void testToDto_RecreatedValue_EqualsValue() {
        // given
        var value = createDummyValue();

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

        // when
        var value = fromDto(valueDto);
        var recreatedValueDto = toDto(value);

        // then
        assertThat(valueDto).isEqualTo(recreatedValueDto);
    }
}
