package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.entity.ImpactValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.evatool.impact.common.TestDataGenerator.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract public class RepositoryTest {

    @Autowired
    protected ImpactRepository impactRepository;

    @Autowired
    protected ImpactValueRepository valueRepository;

    @Autowired
    protected ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    protected ImpactAnalysisRepository impactAnalysisRepository;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactRepository.deleteAll();
        stakeholderRepository.deleteAll();
        valueRepository.deleteAll();
        impactAnalysisRepository.deleteAll();
    }

    protected Impact saveFullDummyImpact() {
        var value = saveDummyValue();
        var stakeholder = saveDummyStakeholder();
        var analysis = saveDummyAnalysis();
        var impact = createDummyImpact(analysis);
        impact.setValueEntity(value);
        impact.setStakeholder(stakeholder);
        return impactRepository.save(impact);
    }

    protected Impact saveFullDummyImpact(ImpactAnalysis analysis) {
        var value = saveFullDummyValue(analysis);
        var stakeholder = saveDummyStakeholder();
        var impact = createDummyImpact(analysis);
        impact.setValueEntity(value);
        impact.setStakeholder(stakeholder);
        return impactRepository.save(impact);
    }

    protected ImpactValue saveDummyValue() {
        var analysis = saveDummyAnalysis();
        return valueRepository.save(createDummyValue(analysis));
    }

    protected ImpactValue saveFullDummyValue(ImpactAnalysis analysis) {
        return valueRepository.save(createDummyValue(analysis));
    }

    protected ImpactStakeholder saveDummyStakeholder() {
        return stakeholderRepository.save(createDummyStakeholder());
    }

    protected ImpactAnalysis saveDummyAnalysis() {
        return impactAnalysisRepository.save(createDummyAnalysis());
    }
}
