package com.evatool.domain.repository;

import com.evatool.domain.entity.Analysis;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnalysisRepositoryTest extends CrudRepositoryTest<Analysis> {

    @Test
    void testCascadeDelete() {
        // given
        var analysis = getPersistedAnalysis();
        var stakeholder = getPersistedStakeholder(analysis);
        var value = getPersistedValue(analysis);
        var impact = getPersistedImpact(analysis);
        var requirement = getPersistedRequirement(analysis);
        var requirementDelta = getPersistedRequirementDelta(impact, requirement);
        var variant = getPersistedVariant(analysis);

        // when
        analysisRepository.delete(analysis);

        // then
        assertThat(stakeholderRepository.findAll()).isEmpty();
        assertThat(valueRepository.findAll()).isEmpty();
        assertThat(impactRepository.findAll()).isEmpty();
        assertThat(requirementRepository.findAll()).isEmpty();
        assertThat(requirementDeltaRepository.findAll()).isEmpty();
        assertThat(variantRepository.findAll()).isEmpty();
    }
}
