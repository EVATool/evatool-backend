package com.evatool.application.json.mapper;

import com.evatool.application.json.AnalysisJson;
import com.evatool.domain.entity.Analysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisJsonMapper {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisJsonMapper.class);

    @SneakyThrows
    public AnalysisJson toJson(Analysis entity) {
        logger.debug("To Json");
        var json = new AnalysisJson(
                entity.getName(),
                entity.getDescription(),
                entity.getIsTemplate(),
                entity.getImageUrl()
        );
        //super.amendToJson(json, entity); // TODO make json mapping like dto mapping!
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(new Object());

        return json;
    }
}
