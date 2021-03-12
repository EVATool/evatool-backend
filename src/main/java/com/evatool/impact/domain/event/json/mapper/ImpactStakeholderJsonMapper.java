package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.common.exception.EventPayloadInvalidException;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.event.json.ImpactStakeholderJson;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactStakeholderJsonMapper {

    private ImpactStakeholderJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactStakeholderJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactStakeholder fromJson(String json) {
        logger.info("Mapping Json to Entity");
        try {
            var impactStakeholderJson = new Gson().fromJson(json, ImpactStakeholderJson.class);
            return modelMapper.map(impactStakeholderJson, ImpactStakeholder.class);
        } catch (JsonSyntaxException | IllegalArgumentException | MappingException ex) {
            throw new EventPayloadInvalidException(json, ex);
        }
    }
}
