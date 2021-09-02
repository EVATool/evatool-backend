package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.domain.entity.RequirementDelta;
import com.evatool.domain.repository.ImpactRepository;
import com.evatool.domain.repository.RequirementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class RequirementDeltaMapper extends SuperMapper<RequirementDelta, RequirementDeltaDto> {

    private static final Logger logger = LoggerFactory.getLogger(RequirementDeltaMapper.class);

    private final ImpactRepository impactRepository;

    private final RequirementRepository requirementRepository;

    public RequirementDeltaMapper(ImpactRepository impactRepository, RequirementRepository requirementRepository) {
        logger.trace("Constructor");
        this.impactRepository = impactRepository;
        this.requirementRepository = requirementRepository;
    }

    @Override
    public RequirementDeltaDto toDto(RequirementDelta entity) {
        logger.trace("To Dto");
        var dto = new RequirementDeltaDto(
                entity.getOverwriteMerit(),
                entity.getOriginalMerit(),
                entity.getMinOverwriteMerit(),
                entity.getMaxOverwriteMerit(),
                colorToHex(entity.getMeritColor()),
                entity.getImpact().getId(),
                entity.getRequirement().getId(),
                entity.getRequirement().getAnalysis().getId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public RequirementDelta fromDto(RequirementDeltaDto dto) {
        logger.trace("From Dto");
        var entity = new RequirementDelta(
                findByIdOrThrowIfEmpty(impactRepository, dto.getImpactId()),
                findByIdOrThrowIfEmpty(requirementRepository, dto.getRequirementId()),
                dto.getOverwriteMerit()
        );
        super.amendFromDto(entity, dto);
        return entity;
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
