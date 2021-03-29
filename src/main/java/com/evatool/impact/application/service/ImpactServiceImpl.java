package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import com.evatool.impact.common.exception.*;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.event.ImpactEventPublisher;
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

    private final ImpactValueService impactValueService;

    private final ImpactStakeholderService impactStakeholderService;

    private final ImpactAnalysisService impactAnalysisService;

    private final ImpactEventPublisher impactEventPublisher;

    public ImpactServiceImpl(ImpactRepository impactRepository, ImpactValueService valueService, ImpactStakeholderService impactStakeholderService, ImpactAnalysisService impactAnalysisService, ImpactEventPublisher impactEventPublisher) {
        this.impactRepository = impactRepository;
        this.impactValueService = valueService;
        this.impactStakeholderService = impactStakeholderService;
        this.impactAnalysisService = impactAnalysisService;
        this.impactEventPublisher = impactEventPublisher;
    }


    @Override
    public ImpactDto findById(UUID id) {
        logger.info("Get Impact");
        if (id == null) {
            throw new EntityIdRequiredException(Impact.class.getSimpleName());
        }
        var impact = impactRepository.findById(id);
        if (impact.isEmpty()) {
            throw new EntityNotFoundException(Impact.class, id);
        }
        return ImpactDtoMapper.toDto(impact.get());
    }

    @Override
    public List<ImpactDto> findAll() {
        logger.info("Get Impacts");
        var impacts = impactRepository.findAll();
        var impactDtoList = new ArrayList<ImpactDto>();
        impacts.forEach(impact -> impactDtoList.add(ImpactDtoMapper.toDto(impact)));
        return impactDtoList;
    }

    @Override
    public List<ImpactDto> findAllByAnalysisId(UUID analysisId) {
        logger.info("Get Impacts By Analysis Id");
        var impacts = impactRepository.findAllByAnalysisId(analysisId);
        var impactDtoList = new ArrayList<ImpactDto>();
        impacts.forEach(impact -> impactDtoList.add(ImpactDtoMapper.toDto(impact)));
        return impactDtoList;
    }

    @Override
    public ImpactDto create(ImpactDto impactDto) {
        logger.info("Create Impact");
        if (impactDto.getId() != null) {
            throw new EntityIdMustBeNullException(Impact.class.getSimpleName());
        }
        if (impactDto.getUniqueString() != null) {
            throw new UniqueStringMustBeNullException(Impact.class.getSimpleName());
        }
        this.assertImpactChildrenExist(impactDto);
        var impact = impactRepository.save(ImpactDtoMapper.fromDto(impactDto));
        impactEventPublisher.publishImpactCreated(impact);
        return ImpactDtoMapper.toDto(impact);
    }

    @Override
    public ImpactDto update(ImpactDto impactDto) {
        logger.info("Update Impact");
        var persisted = this.findById(impactDto.getId());
        if (!persisted.getUniqueString().equals(impactDto.getUniqueString())) {
            throw new UniqueStringCannotBeUpdatedException(Impact.class.getSimpleName());
        }
        this.assertImpactChildrenExist(impactDto);
        var impact = impactRepository.save(ImpactDtoMapper.fromDto(impactDto));
        impactEventPublisher.publishImpactUpdated(impact);
        return ImpactDtoMapper.toDto(impact);
    }

    @Override
    public void deleteById(UUID id) {
        logger.info("Delete Impact");
        var impactDto = this.findById(id);
        var impact = ImpactDtoMapper.fromDto(impactDto);
        impactRepository.delete(impact);
        impactEventPublisher.publishImpactDeleted(impact);
    }

    @Override
    public void deleteAll() {
        logger.info("Delete Impacts");
        impactRepository.deleteAll();
    }

    private void assertImpactChildrenExist(ImpactDto impactDto) {
        this.impactStakeholderService.findById(impactDto.getStakeholder().getId());
        this.impactValueService.findById(impactDto.getValueEntity().getId());
        this.impactAnalysisService.findById(impactDto.getAnalysis().getId());
    }
}
