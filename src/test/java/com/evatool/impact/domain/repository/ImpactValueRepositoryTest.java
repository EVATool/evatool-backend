package com.evatool.impact.domain.repository;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ImpactValueRepositoryTest extends RepositoryTest {

    @Test
    void testFindAllByAnalysisId_AnalysisWithTwoImpactValues_ReturnImpactValuesByAnalysisId() {
        // given
        var analysis = saveDummyAnalysis();
        var impactValue1 = saveFullDummyImpact(analysis).getValueEntity();
        var impactValue2 = saveFullDummyImpact(analysis).getValueEntity();

        // when

        // then
        var impactValuesOfAnalysis = valueRepository.findAllByAnalysisId(impactValue1.getAnalysis().getId());
        assertThat(impactValuesOfAnalysis).isEqualTo(Arrays.asList(impactValue1, impactValue2));
    }
}
