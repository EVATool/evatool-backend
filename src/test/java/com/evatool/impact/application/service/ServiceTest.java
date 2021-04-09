package com.evatool.impact.application.service;

import com.evatool.impact.domain.entity.*;
import com.evatool.impact.domain.repository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.impact.common.TestDataGenerator.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract public class ServiceTest {

    @Autowired
    protected ImpactService impactService;

    @Autowired
    protected ImpactRepository impactRepository;

    @Autowired
    protected ImpactValueRepository valueRepository;

    @Autowired
    protected ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    protected ImpactAnalysisRepository impactAnalysisRepository;

    @Autowired
    protected ImpactRequirementRepository requirementRepository;

    @Autowired
    protected ImpactStakeholderService stakeholderService;

    @Autowired
    protected ImpactAnalysisService analysisService;

    @Autowired
    protected ImpactValueService valueService;

    @Autowired
    protected ImpactRequirementService requirementService;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactRepository.deleteAll();
        stakeholderRepository.deleteAll();
        valueRepository.deleteAll();
        impactAnalysisRepository.deleteAll();
        requirementRepository.deleteAll();
    }

    protected Impact saveFullDummyImpact() {
        var analysis = saveFullDummyAnalysis();
        return saveFullDummyImpact(analysis);
    }

    protected Impact saveFullDummyImpact(ImpactAnalysis analysis) {
        var value = saveFullDummyValue(analysis);
        var stakeholder = saveFullDummyStakeholder();
        var impact = createDummyImpact(analysis);
        impact.setValueEntity(value);
        impact.setStakeholder(stakeholder);
        return impactRepository.save(impact);
    }

    protected ImpactValue saveFullDummyValue() {
        var analysis = saveFullDummyAnalysis();
        return valueRepository.save(createDummyValue(analysis));
    }

    protected ImpactValue saveFullDummyValue(ImpactAnalysis analysis) {
        return valueRepository.save(createDummyValue(analysis));
    }

    protected ImpactStakeholder saveFullDummyStakeholder() {
        return stakeholderRepository.save(createDummyStakeholder());
    }

    protected ImpactAnalysis saveFullDummyAnalysis() {
        return impactAnalysisRepository.save(createDummyAnalysis());
    }

    protected ImpactRequirement saveFullDummyRequirement() {
        return requirementRepository.save(createDummyRequirement());
    }
}
