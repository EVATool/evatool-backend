package com.evatool.impact.application.dto.mapper;

import com.evatool.impact.application.dto.ImpactAnalysisDto;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactAnalysisDtoMapper {

    private ImpactAnalysisDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactAnalysisDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactAnalysis fromDto(ImpactAnalysisDto impactAnalysisDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(impactAnalysisDto, ImpactAnalysis.class);

    }

    public static ImpactAnalysisDto toDto(ImpactAnalysis impactAnalysis) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(impactAnalysis, ImpactAnalysisDto.class);
    }
}
