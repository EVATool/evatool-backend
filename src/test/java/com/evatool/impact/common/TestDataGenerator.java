package com.evatool.impact.common;

import com.evatool.impact.application.dto.DimensionDto;
import com.evatool.impact.application.dto.ImpactAnalysisDto;
import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.ImpactStakeholderDto;
import com.evatool.impact.application.dto.mapper.DimensionDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.domain.entity.Dimension;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;

import java.util.UUID;

public class TestDataGenerator {

    public static Dimension createDummyDimension() {
        return new Dimension("dummyDimension", DimensionType.ECONOMIC, "dummyDimensionDescription");
    }

    public static ImpactStakeholder createDummyStakeholder() {
        return new ImpactStakeholder(UUID.randomUUID(), "dummyStakeholder");
    }

    public static Impact createDummyImpact() {
        return new Impact(0.0, "dummyImpactDescription", createDummyDimension(), createDummyStakeholder(), createDummyAnalysis());
    }

    public static ImpactAnalysis createDummyAnalysis() {
        return new ImpactAnalysis(UUID.randomUUID());
    }

    public static DimensionDto createDummyDimensionDto() {
        return DimensionDtoMapper.toDto(createDummyDimension());
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
