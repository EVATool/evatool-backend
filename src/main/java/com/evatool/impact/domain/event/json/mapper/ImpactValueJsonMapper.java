package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.domain.entity.ImpactValue;
import com.evatool.impact.domain.event.json.ImpactValueJson;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactValueJsonMapper {

    private ImpactValueJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static String toJson(ImpactValue impactValue) {
        logger.info("Mapping Entity to Json");
        var valueJson = modelMapper.map(impactValue, ImpactValueJson.class);
        return new Gson().toJson(valueJson);
    }
}
