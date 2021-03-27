package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.domain.entity.Value;
import com.evatool.impact.domain.event.json.ValueJson;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueJsonMapper {

    private ValueJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ValueJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static String toJson(Value value) {
        logger.info("Mapping Entity to Json");
        var valueJson = modelMapper.map(value, ValueJson.class);
        return new Gson().toJson(valueJson);
    }
}
