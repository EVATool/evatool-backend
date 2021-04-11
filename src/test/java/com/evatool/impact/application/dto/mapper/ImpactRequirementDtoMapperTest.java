package com.evatool.impact.application.dto.mapper;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyRequirement;
import static com.evatool.impact.common.TestDataGenerator.createDummyRequirementDto;
import static org.assertj.core.api.Assertions.assertThat;

public class ImpactRequirementDtoMapperTest {

    @Test
    void testToDto_RecreatedRequirement_EqualsRequirement() {
        // given
        var requirement = createDummyRequirement();

        // then
        var requirementDto = ImpactRequirementDtoMapper.toDto(requirement);
        var recreatedRequirement = ImpactRequirementDtoMapper.fromDto(requirementDto);

        // when
        assertThat(requirement).isEqualTo(recreatedRequirement);
    }

    @Test
    void testFromDto_RecreatedRequirementDto_EqualsRequirementDto() {
        // given
        var requirementDto = createDummyRequirementDto();

        // then
        var requirement = ImpactRequirementDtoMapper.fromDto(requirementDto);
        var recreatedRequirementDto = ImpactRequirementDtoMapper.toDto(requirement);

        // when
        assertThat(requirementDto).isEqualTo(recreatedRequirementDto);
    }
}
