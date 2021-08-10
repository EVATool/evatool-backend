package com.evatool.application.mapper;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.domain.entity.Analysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnalysisMapper extends PrefixIdMapper<Analysis, AnalysisDto> {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisMapper.class);

    @Override
    public AnalysisDto toDto(Analysis entity) {
        logger.debug("To Dto");
        var dto = new AnalysisDto(
                entity.getName(),
                entity.getDescription(),
                entity.getIsTemplate(),
                entity.getImageUrl(),
                entity.getLastUpdated(),
                entity.getLastUpdatedPreformatted()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Analysis fromDto(AnalysisDto dto) {
        logger.debug("From Dto");
        var entity = new Analysis(
                dto.getName(),
                dto.getDescription(),
                dto.getIsTemplate(),
                dto.getImageUrl()
        );
        super.amendFromDto(entity, dto);
        return entity;
    }

    @SneakyThrows // TODO Remove or handle somewhere.
    //@Override
    public String toJson(AnalysisDto dto) {
        logger.debug("To Json");
        var mapper = new ObjectMapper();
        var analysisJson = mapper.writeValueAsString(dto);
        return analysisJson;
    }

    @SneakyThrows // TODO Remove or handle somewhere.
    //@Override
    public AnalysisDto fromJson(String json) {
        logger.debug("From Json");
        var mapper = new ObjectMapper();
        var analysisDto = mapper.readValue(json, AnalysisDto.class); // TODO Make mapper ignore stuff which is not required.
        return analysisDto;
    }
}
