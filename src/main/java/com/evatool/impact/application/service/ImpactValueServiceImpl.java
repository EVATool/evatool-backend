package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactValueDto;
import com.evatool.impact.application.dto.ImpactValueDtoMapper;
import com.evatool.impact.common.ImpactValueType;
import com.evatool.impact.common.exception.EntityIdMustBeNullException;
import com.evatool.impact.common.exception.EntityIdRequiredException;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.domain.entity.Value;
import com.evatool.impact.domain.event.json.ImpactValueEventPublisher;
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

    private final ImpactValueEventPublisher impactValueEventPublisher;

    public ImpactValueServiceImpl(ImpactValueRepository impactValueRepository, ImpactValueEventPublisher impactValueEventPublisher) {
        this.impactValueRepository = impactValueRepository;
        this.impactValueEventPublisher = impactValueEventPublisher;
    }

    @Override
    public ImpactValueDto findById(UUID id) {
        logger.info("Get Value");
        if (id == null) {
            throw new EntityIdRequiredException(Value.class.getSimpleName());
        }
        var value = impactValueRepository.findById(id);
        if (value.isEmpty()) {
            throw new EntityNotFoundException(Value.class, id);
        }
        return ImpactValueDtoMapper.toDto(value.get());
    }

    @Override
    public List<ImpactValueDto> findAllByType(ImpactValueType type) {
        logger.info("Get Value by type");
        var values = impactValueRepository.findAllByType(type);
        var valueDtoList = new ArrayList<ImpactValueDto>();
        values.forEach(value -> valueDtoList.add(ImpactValueDtoMapper.toDto(value)));
        return valueDtoList;
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
    public List<ImpactValueType> findAllTypes() {
        logger.info("Get Values Types");
        return Arrays.asList(ImpactValueType.values());
    }

    @Override
    public ImpactValueDto create(ImpactValueDto valuesDto) {
        logger.info("Create Values");
        if (valuesDto.getId() != null) {
            throw new EntityIdMustBeNullException(Value.class.getSimpleName());
        }
        var values = impactValueRepository.save(ImpactValueDtoMapper.fromDto(valuesDto));
        impactValueEventPublisher.publishValueCreated(values);
        return ImpactValueDtoMapper.toDto(values);
    }

    @Override
    public ImpactValueDto update(ImpactValueDto impactValueDto) {
        logger.info("Update Values");
        this.findById(impactValueDto.getId());
        var value = impactValueRepository.save(ImpactValueDtoMapper.fromDto(impactValueDto));
        impactValueEventPublisher.publishValueUpdated(value);
        return ImpactValueDtoMapper.toDto(value);
    }

    @Override
    public void deleteById(UUID id) {
        logger.info("Delete Value");
        var valueDto = this.findById(id);
        var value = ImpactValueDtoMapper.fromDto(valueDto);
        impactValueRepository.delete(value);
        impactValueEventPublisher.publishValueDeleted(value);
    }

    @Override
    public void deleteAll() {
        logger.info("Delete Value");
        impactValueRepository.deleteAll();
    }
}
