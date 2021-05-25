package com.evatool.application.service.impl;

import com.evatool.application.dto.ValueDto;
import com.evatool.application.mapper.ValueMapper;
import com.evatool.application.service.api.ValueService;
import com.evatool.common.enums.ValueType;
import com.evatool.domain.entity.Value;
import com.evatool.domain.repository.ValueRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ValueServiceImpl extends CrudServiceImpl<Value, ValueDto> implements ValueService {

    private static final Logger logger = LoggerFactory.getLogger(ValueServiceImpl.class);

    @Getter
    private final ValueRepository repository;

    @Getter
    private final ValueMapper mapper;

    public ValueServiceImpl(ValueRepository repository, ValueMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Iterable<ValueType> findAllValueTypes() {
        logger.debug("Find All Value Types");
        return Arrays.asList(ValueType.values());
    }
}
