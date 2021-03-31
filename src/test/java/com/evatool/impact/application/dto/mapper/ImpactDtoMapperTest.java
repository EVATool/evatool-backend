package com.evatool.impact.application.dto.mapper;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.impact.application.dto.mapper.ImpactDtoMapper.fromDto;
import static com.evatool.impact.application.dto.mapper.ImpactDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactDtoMapperTest {

    @Test
    void testToDto_RecreatedImpact_EqualsImpact() {
        // given
        var impact = createDummyImpact();
        impact.setId(UUID.randomUUID());

        // when
        var impactDto = toDto(impact);
        var recreatedImpact = fromDto(impactDto);

        // then
        assertThat(impact).isEqualTo(recreatedImpact);
    }

    @Test
    void testFromDto_RecreatedImpactDto_EqualsImpactDto() {
        // given
        var impactDto = createDummyImpactDto();
        impactDto.setId(UUID.randomUUID());
        impactDto.setValueEntity(createDummyValueDto());
        impactDto.setStakeholder(createDummyStakeholderDto());

        // when
        var impact = fromDto(impactDto);
        var recreatedImpactDto = toDto(impact);

        // then
        assertThat(impactDto).isEqualTo(recreatedImpactDto);
    }
}
