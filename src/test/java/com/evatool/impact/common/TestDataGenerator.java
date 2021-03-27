package com.evatool.impact.common;

import com.evatool.impact.application.dto.ImpactAnalysisDto;
import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.ImpactStakeholderDto;
import com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.domain.entity.*;

import java.util.UUID;

public class TestDataGenerator {

    public static Value createDummyDimension() {
        return new Value("dummyDimension", ValueType.ECONOMIC, "dummyDimensionDescription");
    }

    public static ImpactStakeholder createDummyStakeholder() {
        return new ImpactStakeholder(UUID.randomUUID(), "dummyStakeholder");
    }

    public static Impact createDummyImpact() {
        return new Impact(0.0, "dummyImpactDescription", createDummyDimension(), createDummyStakeholder(), createDummyAnalysis());
    }

    public static Impact createDummyImpact(ImpactAnalysis analysis) {
        return new Impact(0.0, "dummyImpactDescription", createDummyDimension(), createDummyStakeholder(), analysis);
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
}
