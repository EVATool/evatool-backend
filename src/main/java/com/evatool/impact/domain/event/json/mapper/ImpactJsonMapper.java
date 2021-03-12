package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.event.json.ImpactJson;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactJsonMapper {

    private ImpactJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static String toJson(Impact impact) {
        logger.info("Mapping Entity to Json");
        var impactJson = modelMapper.map(impact, ImpactJson.class);
        return new Gson().toJson(impactJson);
    }
}
