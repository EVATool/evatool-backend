package com.evatool.impact.application.dto.mapper;

import com.evatool.impact.application.dto.ImpactRequirementDto;
import com.evatool.impact.domain.entity.ImpactRequirement;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactRequirementDtoMapper {

    private ImpactRequirementDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactRequirementDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactRequirement fromDto(ImpactRequirementDto impactRequirementDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(impactRequirementDto, ImpactRequirement.class);
    }

    public static ImpactRequirementDto toDto(ImpactRequirement impactRequirement) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(impactRequirement, ImpactRequirementDto.class);
    }
}
