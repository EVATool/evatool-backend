package com.evatool.application.dto.mapper;

import com.evatool.application.dto.ValueDto;
import com.evatool.domain.entity.Value;
import com.evatool.domain.repository.AnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValueDtoMapper extends SuperDtoMapper<Value, ValueDto> {

    private static final Logger logger = LoggerFactory.getLogger(ValueDtoMapper.class);

    private final AnalysisRepository analysisRepository;

    public ValueDtoMapper(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public ValueDto toDto(Value entity) {
        logger.debug("To Dto");
        var dto = new ValueDto(
                entity.getName(),
                entity.getDescription(),
                entity.getType(),
                entity.getArchived(),
                entity.getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Value fromDto(ValueDto dto) {
        logger.debug("From Dto");
        var entity = new Value(
                dto.getName(),
                dto.getDescription(),
                dto.getType(),
                dto.getArchived(),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
