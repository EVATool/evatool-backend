package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.Dimension;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.evatool.impact.common.TestDataGenerator.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTest {

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
}
