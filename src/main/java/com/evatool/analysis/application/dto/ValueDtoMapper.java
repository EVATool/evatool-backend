package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.model.Value;
import com.evatool.analysis.domain.repository.ValueRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValueDtoMapper {

    private ValueDtoMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ValueDtoMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private static Value value;

    @Autowired
    private static ValueRepository valueRepository;



    public static Value fromDto(ValueDto valueDto) {
        logger.info("Mapping Dto to Entity");
        if (valueDto.getGuiId().equals("")){
            value.setGuiId(generateGuiId());
        }
        return modelMapper.map(valueDto, Value.class);
    }

    public static ValueDto toDto(Value value) {
        logger.info("Mapping Entity to Dto");
        return modelMapper.map(value, ValueDto.class);
    }

    public static String generateGuiId(){
        List<Value> valueList = valueRepository.findAll();
        return String.format("VAL%d",valueList.size() + 1 );
    }
}

