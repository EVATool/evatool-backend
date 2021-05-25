package com.evatool.application.mapper;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.domain.entity.Analysis;
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
                entity.getLastUpdated()
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
}
