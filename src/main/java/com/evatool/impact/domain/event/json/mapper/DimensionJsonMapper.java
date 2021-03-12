package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.domain.entity.Dimension;
import com.evatool.impact.domain.event.json.DimensionJson;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DimensionJsonMapper {

    private DimensionJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(DimensionJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static String toJson(Dimension dimension) {
        logger.info("Mapping Entity to Json");
        var dimensionJson = modelMapper.map(dimension, DimensionJson.class);
        return new Gson().toJson(dimensionJson);
    }
}
