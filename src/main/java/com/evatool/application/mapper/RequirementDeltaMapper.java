package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.domain.entity.RequirementDelta;
import com.evatool.domain.repository.ImpactRepository;
import com.evatool.domain.repository.RequirementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequirementDeltaMapper extends SuperMapper<RequirementDelta, RequirementDeltaDto> {

    private static final Logger logger = LoggerFactory.getLogger(RequirementDeltaMapper.class);

    private final ImpactRepository impactRepository;

    private final RequirementRepository requirementRepository;

    public RequirementDeltaMapper(ImpactRepository impactRepository, RequirementRepository requirementRepository) {
        this.impactRepository = impactRepository;
        this.requirementRepository = requirementRepository;
    }

    @Override
    public RequirementDeltaDto toDto(RequirementDelta entity) {
        logger.debug("To Dto");
        var dto = new RequirementDeltaDto(
                entity.getOverwriteMerit(),
                entity.getOriginalMerit(),
                entity.getOriginalMerit() > 0 ? -1 : entity.getOriginalMerit(),
                entity.getOriginalMerit() < 0 ? 1 : entity.getOriginalMerit(),
                entity.getOriginalMerit() > 0,
                entity.getMeritColor(),
                entity.getImpact().getId(),
                entity.getRequirement().getId(),
                entity.getRequirement().getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public RequirementDelta fromDto(RequirementDeltaDto dto) {
        logger.debug("From Dto");
        var entity = new RequirementDelta(
                dto.getOverwriteMerit(),
                findByIdOrThrowIfEmpty(impactRepository, dto.getImpactId()),
                findByIdOrThrowIfEmpty(requirementRepository, dto.getRequirementId())
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
