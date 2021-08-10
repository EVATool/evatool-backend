package com.evatool.application.dto.mapper;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.domain.entity.Stakeholder;
import com.evatool.domain.repository.AnalysisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StakeholderDtoMapper extends PrefixIdDtoMapper<Stakeholder, StakeholderDto> {

    private static final Logger logger = LoggerFactory.getLogger(StakeholderDtoMapper.class);

    private final AnalysisRepository analysisRepository;

    public StakeholderDtoMapper(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public StakeholderDto toDto(Stakeholder entity) {
        logger.debug("To Dto");
        var dto = new StakeholderDto(
                entity.getName(),
                entity.getPriority(),
                entity.getLevel(),
                entity.getAnalysis().getId(),
                entity.getImpacted()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Stakeholder fromDto(StakeholderDto dto) {
        logger.debug("From Dto");
        var entity = new Stakeholder(
                dto.getName(),
                dto.getPriority(),
                dto.getLevel(),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
