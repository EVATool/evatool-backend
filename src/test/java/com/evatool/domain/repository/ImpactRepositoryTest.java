package com.evatool.domain.repository;

import com.evatool.domain.entity.Impact;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class ImpactRepositoryTest extends CrudRepositoryTest<Impact> implements FindByAnalysisRepositoryTest {

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
        impactRepository.delete(impact);

        // then
        assertThat(requirementDeltaRepository.findAll()).isEmpty();
    }
}
