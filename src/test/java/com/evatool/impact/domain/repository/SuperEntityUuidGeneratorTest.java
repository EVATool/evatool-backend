package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.Dimension;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

class SuperEntityUuidGeneratorTest extends RepositoryTest {

    @Autowired
    ImpactRepository impactRepository;

    @Autowired
    DimensionRepository dimensionRepository;

    @Autowired
    ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    ImpactAnalysisRepository impactAnalysisRepository;

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
        var impact = createDummyImpact(analysis);
        impact.setDimension(dimension);
        impact.setStakeholder(stakeholder);
        return impactRepository.save(impact);
    }

    private Impact saveFullDummyImpact(ImpactAnalysis analysis) {
        var dimension = saveDummyDimension();
        var stakeholder = saveDummyStakeholder();
        var impact = createDummyImpact(analysis);
        impact.setDimension(dimension);
        impact.setStakeholder(stakeholder);
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
    void testPersist_NullId_IdIsAutoGenerated() {
        // given
        var superEntity = createDummyDimension();

        // when
        var savedSuperEntity = dimensionRepository.save(superEntity);

        // then
        assertThat(savedSuperEntity.getId()).isNotNull();
    }

    @Test
    void testPersist_NotNullId_PresetIdWasInserted() {
        // given
        var superEntity = createDummyDimension();
        var id = UUID.randomUUID();
        superEntity.setId(id);

        // when
        var savedSuperEntity = dimensionRepository.save(superEntity);

        // then
        assertThat(savedSuperEntity.getId()).isNotNull();
        assertThat(savedSuperEntity.getId()).isEqualTo(id);
    }

    @Nested
    class ImpactNumericId {

        @Test
        void testNumericId_InsertedImpact_NumericIdIsGenerated() {
            // given
            var impact = saveFullDummyImpact();

            // when

            // then
            assertThat(impact.getNumericId()).isNotNull();
        }

        @Test
        void testNumericId_InsertMultipleImpactsWithTheSameAnalysis_NumericIdIncrements() {
            // given
            var analysis = saveDummyAnalysis();

            // when
            var impact1 = saveFullDummyImpact(analysis);
            var impact2 = saveFullDummyImpact(analysis);

            // then
            assertThat(impact2.getNumericId()).isEqualTo(impact1.getNumericId() + 1);
        }

        @Test
        void testNumericId_InsertMultipleImpactsWithDifferentAnalyses_NumericIdIncrementsSeparately() {
            // given
            var impact1 = saveFullDummyImpact();
            var impact2 = saveFullDummyImpact();

            // when

            // then
            assertThat(impact2.getNumericId()).isEqualTo(impact1.getNumericId());
        }

        @Test
        void testNumericId_InsertMultipleImpactsWithTheSameAnalysisAndDeleteHighestIdImpact_NumericIdIncrementsFromEverHighest() {
            // given
            var analysis = saveDummyAnalysis();
            var impact1 = saveFullDummyImpact(analysis);
            var impact2 = saveFullDummyImpact(analysis);

            // when
            impactRepository.delete(impact2);
            var impact3 = saveFullDummyImpact(analysis);

            // then
            assertThat(impact3.getNumericId()).isEqualTo(impact1.getNumericId() + 2);
        }

    }
}
