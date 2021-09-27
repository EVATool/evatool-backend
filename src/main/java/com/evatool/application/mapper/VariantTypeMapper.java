package com.evatool.application.mapper;

import com.evatool.application.dto.VariantTypeDto;
import com.evatool.domain.entity.VariantType;
import com.evatool.domain.repository.AnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariantTypeMapper extends SuperMapper<VariantType, VariantTypeDto> {

    private static final Logger logger = LoggerFactory.getLogger(VariantTypeMapper.class);

    private final AnalysisRepository analysisRepository;

    public VariantTypeMapper(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public VariantTypeDto toDto(VariantType entity) {
        logger.trace("To Dto");
        var dto = new VariantTypeDto(
                entity.getName(),
                entity.getDescription(),
                entity.getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public VariantType fromDto(VariantTypeDto dto) {
        logger.trace("From Dto");
        var entity = new VariantType(
                dto.getName(),
                dto.getDescription(),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
