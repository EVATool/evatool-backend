package com.evatool.analysis.application.services;

import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.dto.ValueDtoMapper;
import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.common.error.execptions.EntityIdRequiredException;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.events.json.ValueEventPublisher;
import com.evatool.analysis.domain.model.Value;
import com.evatool.analysis.domain.repository.ValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ValueServiceImpl implements ValueService {

    private static final Logger logger = LoggerFactory.getLogger(ValueServiceImpl.class);

    private final ValueRepository valueRepository;

    private final ValueEventPublisher valueEventPublisher;

    public ValueServiceImpl(ValueRepository valueRepository, ValueEventPublisher valueEventPublisher) {
        this.valueRepository = valueRepository;
        this.valueEventPublisher = valueEventPublisher;
    }

    @Override
    public ValueDto findById(UUID id) {
        logger.info("Get Value");
        if (id == null) {
            throw new EntityIdRequiredException(Value.class.getSimpleName());
        }
        var value = valueRepository.findById(id);
        if (value.isEmpty()) {
            throw new EntityNotFoundException(Value.class, id);
        }
        return ValueDtoMapper.toDto(value.get());
    }

    @Override
    public List<ValueDto> findAllByType(ValueType type) {
        logger.info("Get Value by type");
        var values = valueRepository.findAllByType(type);
        var valueDtoList = new ArrayList<ValueDto>();
        values.forEach(value -> valueDtoList.add(ValueDtoMapper.toDto(value)));
        return valueDtoList;
    }

    @Override
    public List<ValueDto> findAll() {
        logger.info("Get Values");
        var values = valueRepository.findAll();
        var valuesDtoList = new ArrayList<ValueDto>();
        values.forEach(value -> valuesDtoList.add(ValueDtoMapper.toDto(value)));
        return valuesDtoList;
    }

    @Override
    public List<ValueType> findAllTypes() {
        logger.info("Get Values Types");
        return Arrays.asList(ValueType.values());
    }

    @Override
    public ValueDto create(ValueDto valuesDto) {
        logger.info("Create Values");
        var values = valueRepository.save(ValueDtoMapper.fromDto(valuesDto));
        valueEventPublisher.publishValueCreated(values);
        return ValueDtoMapper.toDto(values);
    }

    @Override
    public ValueDto update(ValueDto valueDto) {
        logger.info("Update Values");
        this.findById(valueDto.getId());
        var value = valueRepository.save(ValueDtoMapper.fromDto(valueDto));
        valueEventPublisher.publishValueUpdated(value);
        return ValueDtoMapper.toDto(value);
    }

    @Override
    public void deleteById(UUID id) {
        logger.info("Delete Value");
        var valueDto = this.findById(id);
        var value = ValueDtoMapper.fromDto(valueDto);
        valueRepository.delete(value);
        valueEventPublisher.publishValueDeleted(value);
    }

    @Override
    public void deleteAll() {
        logger.info("Delete Value");
        valueRepository.deleteAll();
    }
}
