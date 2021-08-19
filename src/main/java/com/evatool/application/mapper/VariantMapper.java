package com.evatool.application.mapper;

import com.evatool.application.dto.VariantDto;
import com.evatool.domain.entity.Variant;
import com.evatool.domain.repository.AnalysisRepository;
import com.evatool.domain.repository.VariantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VariantMapper extends PrefixIdMapper<Variant, VariantDto> {

    private static final Logger logger = LoggerFactory.getLogger(VariantMapper.class);

    private final AnalysisRepository analysisRepository;

    public VariantMapper(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public VariantDto toDto(Variant entity) {
        logger.debug("To Dto");
        var dto = new VariantDto(
                entity.getName(),
                entity.getDescription(),
                entity.getArchived(),
                entity.getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Variant fromDto(VariantDto dto) {
        logger.debug("From Dto");
        var entity = new Variant(
                dto.getName(),
                dto.getDescription(),
                dto.getArchived(),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
