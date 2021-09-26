package com.evatool.domain.entity;

import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.enums.ValueType;
import com.evatool.domain.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class EntityTest<S extends SuperEntity> {

    @Autowired
    protected AnalysisRepository analysisRepository;

    @Autowired
    protected ImpactRepository impactRepository;

    @Autowired
    protected RequirementRepository requirementRepository;

    @Autowired
    protected RequirementDeltaRepository requirementDeltaRepository;

    @Autowired
    protected StakeholderRepository stakeholderRepository;

    @Autowired
    protected ValueRepository valueRepository;

    @Autowired
    protected VariantRepository variantRepository;

    @BeforeEach
    protected void clearDatabase() {
        requirementDeltaRepository.deleteAll();
        requirementRepository.deleteAll();
        impactRepository.deleteAll();
        valueRepository.deleteAll();
        stakeholderRepository.deleteAll();
        variantRepository.deleteAll();
        analysisRepository.deleteAll();
    }

    protected Class<S> getEntityClass() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected SuperEntity getFloatingEntity() {
        var type = getEntityClass();
        if (type == Analysis.class) {
            return getFloatingAnalysis();
        } else if (type == Impact.class) {
            return getFloatingImpact();
        } else if (type == Requirement.class) {
            return getFloatingRequirement();
        } else if (type == RequirementDelta.class) {
            return getFloatingRequirementDelta();
        } else if (type == Stakeholder.class) {
            return getFloatingStakeholder();
        } else if (type == Value.class) {
            return getFloatingValue();
        } else if (type == Variant.class) {
            return getFloatingVariant();
        } else {
            throw new IllegalArgumentException("No method found for type " + type.getSimpleName());
        }
    }

    public SuperEntity getPersistedEntity() {
        var type = getEntityClass();
        if (type == Analysis.class) {
            return getPersistedAnalysis();
        } else if (type == Impact.class) {
            return getPersistedImpact();
        } else if (type == Requirement.class) {
            return getPersistedRequirement();
        } else if (type == RequirementDelta.class) {
            return getPersistedRequirementDelta();
        } else if (type == Stakeholder.class) {
            return getPersistedStakeholder();
        } else if (type == Value.class) {
            return getPersistedValue();
        } else if (type == Variant.class) {
            return getPersistedVariant();
        } else {
            throw new IllegalArgumentException("No method found for type " + type.getSimpleName());
        }
    }

    protected Analysis getFloatingAnalysis() {
        return new Analysis("name", "description", false);
    }

    public Analysis getPersistedAnalysis() {
        return analysisRepository.save(getFloatingAnalysis());
    }

    protected Impact getFloatingImpact() {
        var analysis = getPersistedAnalysis();
        return getFloatingImpact(analysis);
    }

    protected Impact getFloatingImpact(Analysis analysis) {
        return new Impact(0.0f, "description", getPersistedValue(analysis), getPersistedStakeholder(analysis), analysis);
    }

    protected Impact getFloatingImpact(Analysis analysis, Value value, Stakeholder stakeholder) {
        return new Impact(0.0f, "description", value, stakeholder, analysis);
    }

    protected Impact getPersistedImpact() {
        return getPersistedImpact(getPersistedAnalysis());
    }

    protected Impact getPersistedImpact(Analysis analysis) {
        return impactRepository.save(getFloatingImpact(analysis));
    }

    protected Impact getPersistedImpact(Analysis analysis, Value value, Stakeholder stakeholder) {
        return impactRepository.save(getFloatingImpact(analysis, value, stakeholder));
    }


    protected Requirement getFloatingRequirement() {
        return getFloatingRequirement(getPersistedAnalysis());
    }

    protected Requirement getFloatingRequirement(Analysis analysis) {
        return new Requirement("description", analysis);
    }

    protected Requirement getPersistedRequirement() {
        return getPersistedRequirement(getPersistedAnalysis());
    }

    protected Requirement getPersistedRequirement(Analysis analysis) {
        return requirementRepository.save(getFloatingRequirement(analysis));
    }

    protected RequirementDelta getFloatingRequirementDelta() {
        var analysis = getPersistedAnalysis();
        return getFloatingRequirementDelta(getPersistedImpact(analysis), getPersistedRequirement(analysis));
    }

    protected RequirementDelta getFloatingRequirementDelta(Impact impact, Requirement requirement) {
        var analysis = getPersistedAnalysis();
        return new RequirementDelta(impact, requirement);
    }

    protected RequirementDelta getPersistedRequirementDelta() {
        var analysis = getPersistedAnalysis();
        return getPersistedRequirementDelta(getPersistedImpact(analysis), getPersistedRequirement(analysis));
    }

    protected RequirementDelta getPersistedRequirementDelta(Impact impact, Requirement requirement) {
        return requirementDeltaRepository.save(getFloatingRequirementDelta(impact, requirement));
    }

    protected Stakeholder getFloatingStakeholder() {
        return getFloatingStakeholder(getPersistedAnalysis());
    }

    protected Stakeholder getFloatingStakeholder(Analysis analysis) {
        return new Stakeholder("name", "description", StakeholderPriority.ONE, StakeholderLevel.INDIVIDUAL, analysis);
    }

    protected Stakeholder getPersistedStakeholder() {
        return getPersistedStakeholder(getPersistedAnalysis());
    }

    protected Stakeholder getPersistedStakeholder(Analysis analysis) {
        return stakeholderRepository.save(getFloatingStakeholder(analysis));
    }

    protected Value getFloatingValue() {
        return getFloatingValue(getPersistedAnalysis());
    }

    protected Value getFloatingValue(Analysis analysis) {
        return new Value("name", "description", ValueType.SOCIAL, false, analysis);
    }

    protected Value getPersistedValue() {
        return getPersistedValue(getPersistedAnalysis());
    }

    protected Value getPersistedValue(Analysis analysis) {
        return valueRepository.save(getFloatingValue(analysis));
    }

    protected Variant getFloatingVariant() {
        return getFloatingVariant(getPersistedAnalysis());
    }

    protected Variant getFloatingVariant(Analysis analysis) {
        return new Variant("name", "description", false, analysis);
    }

    protected Variant getPersistedVariant() {
        return getPersistedVariant(getPersistedAnalysis());
    }

    protected Variant getPersistedVariant(Analysis analysis) {
        return variantRepository.save(getFloatingVariant(analysis));
    }
}
