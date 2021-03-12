package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.common.exception.EventPayloadInvalidException;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.event.json.ImpactAnalysisJson;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpactAnalysisJsonMapper {

    private ImpactAnalysisJsonMapper() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ImpactAnalysisJsonMapper.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ImpactAnalysis fromJson(String json) {
        logger.info("Mapping Json to Entity");
        try {
            var impactAnalysisJson = new Gson().fromJson(json, ImpactAnalysisJson.class);
            return modelMapper.map(impactAnalysisJson, ImpactAnalysis.class);
        } catch (JsonSyntaxException | IllegalArgumentException | MappingException ex) {
            throw new EventPayloadInvalidException(json, ex);
        }
    }
}
