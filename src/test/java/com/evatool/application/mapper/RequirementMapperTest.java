package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDto;
import com.evatool.domain.entity.Requirement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RequirementMapperTest extends SuperMapperTest<Requirement, RequirementDto, RequirementMapper> {

    @Autowired
    private RequirementMapper mapper;

    @Test
    void testFromDto_VariantIdsNull_StillWorks() {
        // given
        var analysis = getPersistedAnalysis();
        var requirement = getPersistedRequirement(analysis);

        // when
        var requirementDto = mapper.toDto(requirement);
        requirementDto.setVariantIds(null);

        // then
        var reconstructedRequirement = mapper.fromDto(requirementDto);
        assertThat(reconstructedRequirement.getVariants()).isEmpty();
    }

    @Test
    void testFromDto_VariantIdsPresent_GetMapped() {
        // given
        var analysis = getPersistedAnalysis();
        var requirement = getPersistedRequirement(analysis);
        var variant1 = getPersistedVariant(analysis);
        var variant2 = getPersistedVariant(analysis);

        // when
        requirement.getVariants().add(variant1);
        requirement.getVariants().add(variant2);
        var requirementDto = mapper.toDto(requirement);

        // then
        var reconstructedRequirement = mapper.fromDto(requirementDto);
        assertThat(reconstructedRequirement.getVariants()).hasSize(2);
    }
}
