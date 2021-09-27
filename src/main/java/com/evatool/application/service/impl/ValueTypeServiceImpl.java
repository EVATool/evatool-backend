package com.evatool.application.service.impl;

import com.evatool.application.dto.ValueTypeDto;
import com.evatool.application.mapper.ValueTypeMapper;
import com.evatool.application.service.api.ValueTypeService;
import com.evatool.domain.entity.ValueType;
import com.evatool.domain.repository.ValueTypeRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValueTypeServiceImpl extends CrudServiceImpl<ValueType, ValueTypeDto> implements ValueTypeService {

    private static final Logger logger = LoggerFactory.getLogger(ValueTypeServiceImpl.class);

    @Getter
    private final ValueTypeRepository repository;

    @Getter
    private final ValueTypeMapper mapper;

    public ValueTypeServiceImpl(ValueTypeRepository repository, ValueTypeMapper mapper) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
    }

    // TODO functional error when still referenced.
}
