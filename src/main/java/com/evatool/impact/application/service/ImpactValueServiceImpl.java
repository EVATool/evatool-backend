package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactValueDto;
import com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper;
import com.evatool.impact.common.exception.EntityIdRequiredException;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.domain.entity.ImpactValue;
import com.evatool.impact.domain.repository.ImpactValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ImpactValueServiceImpl implements ImpactValueService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueServiceImpl.class);

    private final ImpactValueRepository impactValueRepository;

    public ImpactValueServiceImpl(ImpactValueRepository impactValueRepository) {
        this.impactValueRepository = impactValueRepository;
    }

    @Override
    public ImpactValueDto findById(UUID id) {
        logger.info("Get Impact Value");
        if (id == null) {
            throw new EntityIdRequiredException(ImpactValue.class.getSimpleName());
        }
        var value = impactValueRepository.findById(id);
        if (value.isEmpty()) {
            throw new EntityNotFoundException(ImpactValue.class, id);
        }
        return ImpactValueDtoMapper.toDto(value.get());
    }

    // TODO Tests
    @Override
    public List<ImpactValueDto> findAllByAnalysisId(UUID analysisId) {
        logger.info("Get Impact Values By Analysis Id");
        var impactValues = impactValueRepository.findAllByAnalysisId(analysisId);
        var impactValueDtoList = new ArrayList<ImpactValueDto>();
        impactValues.forEach(impactValue -> impactValueDtoList.add(ImpactValueDtoMapper.toDto(impactValue)));
        return impactValueDtoList;
    }

    @Override
    public List<ImpactValueDto> findAll() {
        logger.info("Get Values");
        var values = impactValueRepository.findAll();
        var valuesDtoList = new ArrayList<ImpactValueDto>();
        values.forEach(value -> valuesDtoList.add(ImpactValueDtoMapper.toDto(value)));
        return valuesDtoList;
    }

    @Override
    public void deleteAll() {
        logger.info("Delete ImpactValue");
        impactValueRepository.deleteAll();
    }
}
