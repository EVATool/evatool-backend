package com.evatool.application.mapper;

import com.evatool.application.dto.ValueDto;
import com.evatool.domain.entity.Value;
import com.evatool.domain.repository.AnalysisRepository;
import com.evatool.domain.repository.ValueTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValueMapper extends SuperMapper<Value, ValueDto> {

    private static final Logger logger = LoggerFactory.getLogger(ValueMapper.class);

    private final AnalysisRepository analysisRepository;

    private final ValueTypeRepository valueTypeRepository;

    public ValueMapper(AnalysisRepository analysisRepository, ValueTypeRepository valueTypeRepository) {
        logger.trace("Constructor");
        this.valueTypeRepository = valueTypeRepository;
        this.analysisRepository = analysisRepository;
    }

    @Override
    public ValueDto toDto(Value entity) {
        logger.trace("To Dto");
        var dto = new ValueDto(
                entity.getName(),
                entity.getDescription(),
                entity.getArchived(),
                entity.getType().getId(),
                entity.getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Value fromDto(ValueDto dto) {
        logger.trace("From Dto");
        var entity = new Value(
                dto.getName(),
                dto.getDescription(),
                dto.getArchived(),
                findByIdOrThrowIfEmpty(valueTypeRepository, dto.getValueTypeId()),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
