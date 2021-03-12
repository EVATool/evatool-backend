package com.evatool.impact.application.dto.mapper;

import com.evatool.impact.application.dto.ImpactStakeholderDto;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactStakeholderDtoMapper {

    private ImpactStakeholderDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactStakeholderDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactStakeholder fromDto(ImpactStakeholderDto impactStakeholderDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(impactStakeholderDto, ImpactStakeholder.class);

    }

    public static ImpactStakeholderDto toDto(ImpactStakeholder impactStakeholder) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(impactStakeholder, ImpactStakeholderDto.class);
    }
}
