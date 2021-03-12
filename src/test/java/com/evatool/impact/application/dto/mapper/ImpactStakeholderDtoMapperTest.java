package com.evatool.impact.application.dto.mapper;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper.fromDto;
import static com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholder;
import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholderDto;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactStakeholderDtoMapperTest {

    @Test
    void testToDto_RecreatedStakeholder_EqualsStakeholder() {
        // given
        var stakeholder = createDummyStakeholder();

        // when
        var stakeholderDto = toDto(stakeholder);
        var recreatedStakeholder = fromDto(stakeholderDto);

        // then
        assertThat(stakeholder).isEqualTo(recreatedStakeholder);
    }

    @Test
    void testFromDto_RecreatedStakeholderDto_EqualsStakeholderDto() {
        // given
        var stakeholderDto = createDummyStakeholderDto();

        // when
        var stakeholder = fromDto(stakeholderDto);
        var recreatedStakeholderDto = toDto(stakeholder);

        // then
        assertThat(stakeholderDto).isEqualTo(recreatedStakeholderDto);
    }
}
