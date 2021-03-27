package com.evatool.impact.application.dto;

import com.evatool.impact.domain.entity.Value;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueDtoMapper {

    private ValueDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ValueDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Value fromDto(ValueDto valueDto) {
        logger.info("Mapping Dto to Entity");
        return modelMapper.map(valueDto, Value.class);
    }

    public static ValueDto toDto(Value value) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(value, ValueDto.class);
    }
}
