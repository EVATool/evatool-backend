package com.evatool.impact.application.dto;

import com.evatool.impact.domain.entity.Value;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactValueDtoMapper {

    private ImpactValueDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Value fromDto(ImpactValueDto impactValueDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(impactValueDto, Value.class);
    }

    public static ImpactValueDto toDto(Value value) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(value, ImpactValueDto.class);
    }
}
