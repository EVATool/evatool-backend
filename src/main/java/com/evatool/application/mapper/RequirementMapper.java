package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDto;
import com.evatool.domain.entity.Requirement;
import com.evatool.domain.repository.AnalysisRepository;
import com.evatool.domain.repository.VariantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequirementMapper extends PrefixIdMapper<Requirement, RequirementDto> {

    private static final Logger logger = LoggerFactory.getLogger(RequirementMapper.class);

    private final AnalysisRepository analysisRepository;

    private final VariantRepository variantRepository;

    public RequirementMapper(AnalysisRepository analysisRepository, VariantRepository variantRepository) {
        this.analysisRepository = analysisRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public RequirementDto toDto(Requirement entity) {
        logger.debug("To Dto");
        var dto = new RequirementDto(
                entity.getDescription(),
                entity.getAnalysis().getId(),
                getSuperEntityIds(entity.getVariants())
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public Requirement fromDto(RequirementDto dto) {
        logger.debug("From Dto");
        var entity = new Requirement(
                dto.getDescription(),
                findByIdOrThrowIfEmpty(analysisRepository, dto.getAnalysisId())
        );
        if (dto.getVariantIds() != null) {
            for (var variantId : dto.getVariantIds()) {
                var variant = variantRepository.findById(variantId); // TODO tests
                variant.ifPresent(value -> entity.getVariants().add(value));
            }
        }
        super.amendFromDto(entity, dto);
        return entity;
    }
}