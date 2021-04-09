package com.evatool.impact.common;

import com.evatool.impact.application.dto.*;
import com.evatool.impact.application.dto.mapper.*;
import com.evatool.impact.domain.entity.*;

import java.util.UUID;

public class TestDataGenerator {

    public static ImpactRequirement createDummyRequirement() {
        return new ImpactRequirement(UUID.randomUUID(), "reqTitle", "reqDesc");
    }

    public static ImpactRequirementDto createDummyRequirementDto() {
        return ImpactRequirementDtoMapper.toDto(createDummyRequirement());
    }

    public static ImpactValue createDummyValue() {
        return new ImpactValue(UUID.randomUUID(),"dummyValue", "ECONOMIC", "dummyValueDescription", createDummyAnalysis());
    }

    public static ImpactValue createDummyValue(ImpactAnalysis analysis) {
        return new ImpactValue(UUID.randomUUID(),"dummyValue", "ECONOMIC", "dummyValueDescription", analysis);
    }

    public static ImpactStakeholder createDummyStakeholder() {
        return new ImpactStakeholder(UUID.randomUUID(), "dummyStakeholder", "society");
    }

    public static Impact createDummyImpact() {
        return new Impact(0.0, "dummyImpactDescription", createDummyValue(), createDummyStakeholder(), createDummyAnalysis());
    }

    public static Impact createDummyImpact(ImpactAnalysis analysis) {
        return new Impact(0.0, "dummyImpactDescription", createDummyValue(), createDummyStakeholder(), analysis);
    }

    public static ImpactAnalysis createDummyAnalysis() {
        return new ImpactAnalysis(UUID.randomUUID());
    }

    public static ImpactStakeholderDto createDummyStakeholderDto() {
        return ImpactStakeholderDtoMapper.toDto(createDummyStakeholder());
    }

    public static ImpactDto createDummyImpactDto() {
        return ImpactDtoMapper.toDto(createDummyImpact());
    }

    public static ImpactAnalysisDto createDummyAnalysisDto() {
        return ImpactAnalysisDtoMapper.toDto(createDummyAnalysis());
    }

    public static ImpactValueDto createDummyValueDto() {
        return ImpactValueDtoMapper.toDto(createDummyValue());
    }
}
