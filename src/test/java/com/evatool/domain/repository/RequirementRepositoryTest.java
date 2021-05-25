package com.evatool.domain.repository;

import com.evatool.domain.entity.Requirement;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequirementRepositoryTest extends CrudRepositoryTest<Requirement> implements FindByAnalysisRepositoryTest {

    @Test
    void testCascadeDelete() {
        // given
        var analysis = getPersistedAnalysis();
        var stakeholder = getPersistedStakeholder(analysis);
        var value = getPersistedValue(analysis);
        var impact = getPersistedImpact(analysis);
        var requirement = getPersistedRequirement(analysis);
        var requirementDelta = getPersistedRequirementDelta(impact, requirement);

        // when
        requirementRepository.delete(requirement);

        // then
        assertThat(requirementDeltaRepository.findAll()).isEmpty();
    }
}
