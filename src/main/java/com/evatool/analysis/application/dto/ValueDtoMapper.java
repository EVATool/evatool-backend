package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.model.NumericId;
import com.evatool.analysis.domain.model.Value;
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
        var value = modelMapper.map(valueDto, Value.class);
        if (valueDto.getAnalysis().getUniqueString() != null) {
            var numericId = new NumericId();
            numericId.setNumericId(Integer.valueOf(valueDto.getAnalysis().getUniqueString().replace("ANA", "")));
            value.getAnalysis().setNumericId(numericId);
        }
        return value;
    }

    public static ValueDto toDto(Value value) {
        logger.info("Mapping Entity to Dto");
        var valueDto = modelMapper.map(value, ValueDto.class);
        valueDto.getAnalysis().setRootEntityID(value.getAnalysis().getAnalysisId());
        return valueDto;
    }
}

