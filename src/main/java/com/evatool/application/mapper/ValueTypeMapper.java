package com.evatool.application.mapper;

import com.evatool.application.dto.ValueTypeDto;
import com.evatool.domain.entity.ValueType;
import com.evatool.domain.repository.AnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValueTypeMapper extends SuperMapper<ValueType, ValueTypeDto> {

    private static final Logger logger = LoggerFactory.getLogger(ValueTypeMapper.class);

    private final AnalysisRepository analysisRepository;

    public ValueTypeMapper(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public ValueTypeDto toDto(ValueType entity) {
        logger.trace("To Dto");
        var dto = new ValueTypeDto(
                entity.getName(),
                entity.getDescription(),
                entity.getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public ValueType fromDto(ValueTypeDto dto) {
        logger.trace("From Dto");
        var entity = new ValueType(
                dto.getName(),
                dto.getDescription(),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
