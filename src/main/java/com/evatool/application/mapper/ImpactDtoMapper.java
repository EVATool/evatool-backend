package com.evatool.application.mapper;

import com.evatool.application.dto.ImpactDto;
import com.evatool.domain.entity.Impact;
import com.evatool.domain.repository.AnalysisRepository;
import com.evatool.domain.repository.StakeholderRepository;
import com.evatool.domain.repository.ValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImpactDtoMapper extends PrefixIdDtoMapper<Impact, ImpactDto> {

    private static final Logger logger = LoggerFactory.getLogger(ImpactDtoMapper.class);

    private final ValueRepository valueRepository;

    private final StakeholderRepository stakeholderRepository;

    private final AnalysisRepository analysisRepository;

    public ImpactDtoMapper(ValueRepository valueRepository, StakeholderRepository stakeholderRepository, AnalysisRepository analysisRepository) {
        this.valueRepository = valueRepository;
        this.stakeholderRepository = stakeholderRepository;
        this.analysisRepository = analysisRepository;
    }

    @Override
    public ImpactDto toDto(Impact entity) {
        logger.debug("To Dto");
        var dto = new ImpactDto(
                entity.getMerit(),
                entity.getDescription(),
                entity.getIsGoal(),
                entity.getValue().getId(),
                entity.getStakeholder().getId(),
                entity.getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Impact fromDto(ImpactDto dto) {
        logger.debug("From Dto");
        var entity = new Impact(
                dto.getMerit(),
                dto.getDescription(),
                findByIdOrThrowIfEmpty(valueRepository, dto.getValueId()),
                findByIdOrThrowIfEmpty(stakeholderRepository, dto.getStakeholderId()),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
