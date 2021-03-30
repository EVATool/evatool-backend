package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.common.exception.EventPayloadInvalidException;
import com.evatool.impact.domain.entity.ImpactValue;
import com.evatool.impact.domain.event.json.ImpactValueJson;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactValueJsonMapper {

    private ImpactValueJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactValue fromJson(String json) {
        logger.info("Mapping Json to Entity");
        try {
            var impactValueJson = new Gson().fromJson(json, ImpactValueJson.class);
            return modelMapper.map(impactValueJson, ImpactValue.class);
        } catch (JsonSyntaxException | IllegalArgumentException | MappingException ex) {
            throw new EventPayloadInvalidException(json, ex);
        }
    }
}
