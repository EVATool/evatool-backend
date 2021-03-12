package com.evatool.impact.application.dto.mapper;

import com.evatool.impact.application.dto.DimensionDto;
import com.evatool.impact.domain.entity.Dimension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionDtoMapper {

    private DimensionDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(DimensionDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Dimension fromDto(DimensionDto dimensionDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(dimensionDto, Dimension.class);
    }

    public static DimensionDto toDto(Dimension dimension) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(dimension, DimensionDto.class);
    }
}
