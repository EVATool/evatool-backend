package com.evatool.application.service.impl;

import com.evatool.application.dto.VariantTypeDto;
import com.evatool.application.mapper.VariantTypeMapper;
import com.evatool.application.service.api.VariantTypeService;
import com.evatool.domain.entity.VariantType;
import com.evatool.domain.repository.VariantTypeRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VariantTypeServiceImpl extends CrudServiceImpl<VariantType, VariantTypeDto> implements VariantTypeService {

    private static final Logger logger = LoggerFactory.getLogger(VariantTypeServiceImpl.class);

    @Getter
    private final VariantTypeRepository repository;

    @Getter
    private final VariantTypeMapper mapper;

    public VariantTypeServiceImpl(VariantTypeRepository repository, VariantTypeMapper mapper) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
    }

    // TODO functional error when still referenced.
}