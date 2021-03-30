package com.evatool.impact.application.dto;

import com.evatool.impact.domain.entity.ImpactValue;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactValueDtoMapper {

    private ImpactValueDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactValue fromDto(ImpactValueDto impactValueDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(impactValueDto, ImpactValue.class);
    }

    public static ImpactValueDto toDto(ImpactValue impactValue) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(impactValue, ImpactValueDto.class);
    }
}
