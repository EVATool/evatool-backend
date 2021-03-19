package com.evatool.impact.application.dto.mapper;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.domain.entity.Impact;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactDtoMapper {

    private ImpactDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Impact fromDto(ImpactDto impactDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(impactDto, Impact.class);
    }

    public static ImpactDto toDto(Impact impact) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(impact, ImpactDto.class);
    }
}
