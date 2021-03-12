package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactStakeholderDto;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.common.exception.EntityIdRequiredException;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImpactStakeholderServiceImpl implements ImpactStakeholderService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactStakeholderServiceImpl.class);

    private final ImpactStakeholderRepository stakeholderRepository;

    public ImpactStakeholderServiceImpl(ImpactStakeholderRepository stakeholderRepository) {
        this.stakeholderRepository = stakeholderRepository;
    }

    @Override
    public ImpactStakeholderDto findById(UUID id) {
        logger.info("Get Stakeholder");
        if (id == null) {
            throw new EntityIdRequiredException(ImpactStakeholder.class.getSimpleName());
        }
        var stakeholder = stakeholderRepository.findById(id);
        if (stakeholder.isEmpty()) {
            throw new EntityNotFoundException(ImpactStakeholder.class, id);
        }
        return ImpactStakeholderDtoMapper.toDto(stakeholder.get());
    }

    @Override
    public List<ImpactStakeholderDto> findAll() {
        logger.info("Get Stakeholders");
        var stakeholders = stakeholderRepository.findAll();
        var stakeholderDtoList = new ArrayList<ImpactStakeholderDto>();
        stakeholders.forEach(stakeholder -> stakeholderDtoList.add(ImpactStakeholderDtoMapper.toDto(stakeholder)));
        return stakeholderDtoList;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete Stakeholders");
        stakeholderRepository.deleteAll();
    }
}
