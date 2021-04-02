package com.evatool.impact.application.service;

import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.entity.ImpactValue;
import com.evatool.impact.domain.repository.ImpactValueRepository;
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
    protected ImpactStakeholderService stakeholderService;

    @Autowired
    protected ImpactAnalysisService analysisService;

    @Autowired
    protected ImpactValueService valueService;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactRepository.deleteAll();
        stakeholderRepository.deleteAll();
        valueRepository.deleteAll();
        impactAnalysisRepository.deleteAll();
    }

    protected Impact saveFullDummyImpact() {
        var analysis = saveFullDummyAnalysis();
        return saveFullDummyImpact(analysis);
    }

    protected Impact saveFullDummyImpact(ImpactAnalysis analysis) {
        var value = saveFullDummyValue();
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

    protected ImpactStakeholder saveFullDummyStakeholder() {
        return stakeholderRepository.save(createDummyStakeholder());
    }

    protected ImpactAnalysis saveFullDummyAnalysis() {
        return impactAnalysisRepository.save(createDummyAnalysis());
    }
}
