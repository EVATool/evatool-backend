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

    public static ImpactValueJson fromString(String json) {
        logger.info("Mapping String to Json");
        try {
            return new Gson().fromJson(json, ImpactValueJson.class);
        } catch (JsonSyntaxException ex) {
            throw new EventPayloadInvalidException(json, ex);
        }
    }

    public static ImpactValue fromJson(ImpactValueJson impactValueJson) {
        logger.info("Mapping Json to Entity");
        try {
            return modelMapper.map(impactValueJson, ImpactValue.class);
        } catch (IllegalArgumentException | MappingException ex) {
            throw new EventPayloadInvalidException(impactValueJson.toString(), ex);
        }
    }

    public static ImpactValueJson toJson(ImpactValue impactValue) {
        logger.info("Mapping Entity to Json");
        return modelMapper.map(impactValue, ImpactValueJson.class);
    }

    public static String toString(ImpactValueJson impactValueJson) {
        return new Gson().toJson(impactValueJson);
    }

    public static String toString(ImpactValue impactValue) {
        return toString(toJson(impactValue));
    }
}
