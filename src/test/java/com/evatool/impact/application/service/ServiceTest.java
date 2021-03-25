package com.evatool.impact.application.service;

import com.evatool.impact.domain.entity.Dimension;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.repository.DimensionRepository;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import com.evatool.impact.domain.repository.ImpactRepository;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.impact.common.TestDataGenerator.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest {

    @Autowired
    ImpactService impactService;

    @Autowired
    ImpactRepository impactRepository;

    @Autowired
    DimensionRepository dimensionRepository;

    @Autowired
    ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    ImpactAnalysisRepository impactAnalysisRepository;

    @Autowired
    ImpactStakeholderService stakeholderService;

    @Autowired
    ImpactAnalysisService analysisService;

    @Autowired
    DimensionService dimensionService;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactRepository.deleteAll();
        stakeholderRepository.deleteAll();
        dimensionRepository.deleteAll();
        impactAnalysisRepository.deleteAll();
    }

    protected Impact saveFullDummyImpact() {
        var analysis = saveFullDummyAnalysis();
        return saveFullDummyImpact(analysis);
    }

    protected Impact saveFullDummyImpact(ImpactAnalysis analysis) {
        var dimension = saveFullDummyDimension();
        var stakeholder = saveFullDummyStakeholder();
        var impact = createDummyImpact(analysis);
        impact.setDimension(dimension);
        impact.setStakeholder(stakeholder);
        return impactRepository.save(impact);
    }

    protected ImpactStakeholder saveFullDummyImpactStakeholder() {
        var stakeholder = createDummyStakeholder();
        return stakeholderRepository.save(stakeholder);
    }

    protected Dimension saveFullDummyDimension() {
        return dimensionRepository.save(createDummyDimension());
    }

    protected ImpactStakeholder saveFullDummyStakeholder() {
        return stakeholderRepository.save(createDummyStakeholder());
    }

    protected ImpactAnalysis saveFullDummyAnalysis() {
        return impactAnalysisRepository.save(createDummyAnalysis());
    }
}
