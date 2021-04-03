package com.evatool.analysis.application.dto;

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
        return modelMapper.map(valueDto, Value.class);
    }

    public static ValueDto toDto(Value value) {
        var valueDto = new ValueDto();
        valueDto.setId(value.getId());
        valueDto.setName(value.getName());
        valueDto.setDescription(value.getDescription());
        valueDto.setType(value.getType());

        if (value.getAnalysis() == null) return valueDto;
        var analysisDto = new AnalysisDTO();
        analysisDto.setRootEntityID(value.getAnalysis().getAnalysisId());
        analysisDto.setAnalysisName(value.getAnalysis().getAnalysisName());
        analysisDto.setAnalysisDescription(value.getAnalysis().getDescription());
        analysisDto.setImage(value.getAnalysis().getImage());
        analysisDto.setDate(value.getAnalysis().getLastUpdate());

        valueDto.setAnalysis(analysisDto);

        logger.info("Mapping Entity to Dto");
        return valueDto;
    }
}

