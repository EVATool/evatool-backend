package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import com.evatool.impact.common.exception.EntityIdMustBeNullException;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.common.exception.EntityIdRequiredException;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.event.impact.ImpactCreatedEventPublisher;
import com.evatool.impact.domain.event.impact.ImpactDeletedEventPublisher;
import com.evatool.impact.domain.event.impact.ImpactUpdatedEventPublisher;
import com.evatool.impact.domain.repository.ImpactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImpactServiceImpl implements ImpactService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactServiceImpl.class);

    private final ImpactRepository impactRepository;

    private final ImpactStakeholderServiceImpl impactStakeholderService;

    private final DimensionServiceImpl dimensionService;

    private final ImpactCreatedEventPublisher impactCreatedEventPublisher;

    private final ImpactUpdatedEventPublisher impactUpdatedEventPublisher;

    private final ImpactDeletedEventPublisher impactDeletedEventPublisher;

    public ImpactServiceImpl(ImpactRepository impactRepository, ImpactStakeholderServiceImpl impactStakeholderService, DimensionServiceImpl dimensionService, ImpactCreatedEventPublisher impactCreatedEventPublisher, ImpactUpdatedEventPublisher impactUpdatedEventPublisher, ImpactDeletedEventPublisher impactDeletedEventPublisher) {
        this.impactRepository = impactRepository;
        this.impactStakeholderService = impactStakeholderService;
        this.dimensionService = dimensionService;
        this.impactCreatedEventPublisher = impactCreatedEventPublisher;
        this.impactUpdatedEventPublisher = impactUpdatedEventPublisher;
        this.impactDeletedEventPublisher = impactDeletedEventPublisher;
    }

    @Override
    public ImpactDto findImpactById(UUID id) {
        logger.info("Get Impact");
        if (id == null) {
            throw new EntityIdRequiredException();
        }
        var impact = impactRepository.findById(id);
        if (impact.isEmpty()) {
            throw new EntityNotFoundException(Impact.class, id);
        }
        return ImpactDtoMapper.toDto(impact.get());
    }

    @Override
    public List<ImpactDto> getAllImpacts() {
        logger.info("Get Impacts");
        var impacts = impactRepository.findAll();
        var impactDtoList = new ArrayList<ImpactDto>();
        impacts.forEach(impact -> impactDtoList.add(ImpactDtoMapper.toDto(impact)));
        return impactDtoList;
    }

    @Override
    public ImpactDto createImpact(ImpactDto impactDto) {
        logger.info("Create Impact");
        if (impactDto.getId() != null) {
            throw new EntityIdMustBeNullException();
        }
        this.checkIfChildEntitiesExist(impactDto);
        var impact = impactRepository.save(ImpactDtoMapper.fromDto(impactDto));
        impactCreatedEventPublisher.onImpactCreated(impact);
        return ImpactDtoMapper.toDto(impact);
    }

    @Override
    public ImpactDto updateImpact(ImpactDto impactDto) {
        logger.info("Update Impact");
        this.findImpactById(impactDto.getId());
        this.checkIfChildEntitiesExist(impactDto);
        var impact = impactRepository.save(ImpactDtoMapper.fromDto(impactDto));
        impactUpdatedEventPublisher.onImpactUpdated(impact);
        return ImpactDtoMapper.toDto(impact);
    }

    @Override
    public void deleteImpactById(UUID id) {
        logger.info("Delete Impact");
        var impactDto = this.findImpactById(id);
        var impact = ImpactDtoMapper.fromDto(impactDto);
        impactRepository.delete(impact);
        impactDeletedEventPublisher.onImpactDeleted(impact);
    }

    @Override
    public void deleteImpacts() {
        logger.info("Delete Impacts");
        impactRepository.deleteAll();
    }

    // TODO Set to retrieved value? What if front end changes DimensionDto or ImpactStakeholderDto values?
    //  Only the id of the child entities should matter at this point
    private void checkIfChildEntitiesExist(ImpactDto impactDto) {
        this.impactStakeholderService.findStakeholderById(impactDto.getStakeholder().getId());
        this.dimensionService.findDimensionById(impactDto.getDimension().getId());
    }
}
