package com.evatool.impact.domain.repository;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ImpactRepositoryTest extends RepositoryTest {

    @Test
    void testFindAllByAnalysisId_AnalysisWithTwoImpacts_ReturnImpactsByAnalysisId() {
        // given
        var analysis = saveDummyAnalysis();
        var impact1 = saveFullDummyImpact(analysis);
        var impact2 = saveFullDummyImpact(analysis);

        // when

        // then
        var impactsOfAnalysis = impactRepository.findAllByAnalysisId(impact1.getAnalysis().getId());
        assertThat(impactsOfAnalysis).isEqualTo(Arrays.asList(impact1, impact2));
    }

    @Test
    void testFindAllByStakeholderId_ImpactWithTwoStakeholders_ReturnImpactByStakeholderId() {
        // given
        var stakeholder = saveDummyStakeholder();
        var impact1 = saveFullDummyImpact();
        var impact2 = saveFullDummyImpact();
        impact1.setStakeholder(stakeholder);
        impact2.setStakeholder(stakeholder);

        // when

        // then
        var impactsOfStakeholder = impactRepository.findAllByStakeholderId(stakeholder.getId());
        assertThat(impactsOfStakeholder).isEqualTo(Arrays.asList(impact1, impact2));
    }

    @Test
    void testFindAllByValueEntityId_ImpactWithTwoValues_ReturnImpactByValueId() {
        // given
        var value = saveDummyValue();
        var impact1 = saveFullDummyImpact();
        var impact2 = saveFullDummyImpact();
        impact1.setValueEntity(value);
        impact2.setValueEntity(value);

        // when

        // then
        var impactsOfValue = impactRepository.findAllByValueEntityId(value.getId());
        assertThat(impactsOfValue).isEqualTo(Arrays.asList(impact1, impact2));
    }
}
