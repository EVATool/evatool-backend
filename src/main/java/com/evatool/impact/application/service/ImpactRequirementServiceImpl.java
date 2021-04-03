package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactRequirementDto;
import com.evatool.impact.application.dto.mapper.ImpactRequirementDtoMapper;
import com.evatool.impact.common.exception.EntityIdRequiredException;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.domain.entity.ImpactRequirement;
import com.evatool.impact.domain.repository.ImpactRequirementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImpactRequirementServiceImpl implements ImpactRequirementService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactRequirementServiceImpl.class);

    private final ImpactRequirementRepository requirementRepository;

    public ImpactRequirementServiceImpl(ImpactRequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @Override
    public ImpactRequirementDto findById(UUID id) {
        logger.info("Get Requirement");
        if (id == null) {
            throw new EntityIdRequiredException(ImpactRequirement.class.getSimpleName());
        }
        var requirement = requirementRepository.findById(id);
        if (requirement.isEmpty()) {
            throw new EntityNotFoundException(ImpactRequirement.class, id);
        }
        return ImpactRequirementDtoMapper.toDto(requirement.get());
    }

    @Override
    public List<ImpactRequirementDto> findAll() {
        logger.info("Get all Requirements");
        var requirements = requirementRepository.findAll();
        var requirementDtoList = new ArrayList<ImpactRequirementDto>();
        requirements.forEach(requirement -> requirementDtoList.add(ImpactRequirementDtoMapper.toDto(requirement)));
        return requirementDtoList;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete Requirements");
        requirementRepository.deleteAll();
    }
}
