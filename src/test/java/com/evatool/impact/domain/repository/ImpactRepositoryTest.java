package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImpactRepositoryTest {

    @Autowired
    ImpactRepository impactRepository;

    @Autowired
    DimensionRepository dimensionRepository;

    @Autowired
    ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    ImpactAnalysisRepository impactAnalysisRepository;

    @Autowired
    NumericIdRepository numericIdRepository;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactRepository.deleteAll();
        stakeholderRepository.deleteAll();
        dimensionRepository.deleteAll();
        impactAnalysisRepository.deleteAll();
    }

    private Impact saveFullDummyImpact() {
        var dimension = saveDummyDimension();
        var stakeholder = saveDummyStakeholder();
        var analysis = saveDummyAnalysis();
        var impact = createDummyImpact();
        impact.setDimension(dimension);
        impact.setStakeholder(stakeholder);
        impact.setAnalysis(analysis);
        return impactRepository.save(impact);
    }

    private Dimension saveDummyDimension() {
        return dimensionRepository.save(createDummyDimension());
    }

    private ImpactStakeholder saveDummyStakeholder() {
        return stakeholderRepository.save(createDummyStakeholder());
    }

    private ImpactAnalysis saveDummyAnalysis() {
        return impactAnalysisRepository.save(createDummyAnalysis());
    }

    @Test
    void testFindAllByAnalysisId_AnalysisWithTwoImpacts_ReturnImpactsByAnalysisId() {
        // given
        var impact1 = saveFullDummyImpact();
        var impact2 = saveFullDummyImpact();

        // when
        impact2.setAnalysis(impact1.getAnalysis());
        impactRepository.save(impact2);

        // then
        var impactsOfAnalysis = impactRepository.findAllByAnalysisId(impact1.getAnalysis().getId());
        assertThat(impactsOfAnalysis).isEqualTo(Arrays.asList(impact1, impact2));
    }

    @Test
    void testSetNumericId_SavedImpact_CannotChangeNumericId() {
        // given
        var impact = saveFullDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setNumericId(1));
    }

    @Test
    void testNumericIdChild_InsertMultipleImpacts_NumericIdIncrements() {
        // given
        var impact1 = saveFullDummyImpact();
        var impact2 = saveFullDummyImpact();

        // when
        impactRepository.save(impact1);
        impactRepository.save(impact2);

        // then
        assertThat(impact2.getNumericId().getId()).isEqualTo(impact1.getNumericId().getId() + 1);
    }

    @Test
    void testNumericIdChild_CreateOperation_NumericIdCascades() {
        // given
        var impact = saveFullDummyImpact();

        // when

        // then
        assertThat(impact.getNumericId().getId()).isNotNull();
    }

    @Test
    void testNumericIdChild_DeleteOperation_NumericIdCascades() {
        // given
        var impact = saveFullDummyImpact();
        var impactNumericId = impact.getNumericId().getId();

        // when
        impactRepository.delete(impact);

        // then

        assertThat(numericIdRepository.findById(impactNumericId)).isNotPresent();
    }
}
