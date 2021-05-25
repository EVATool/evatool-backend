package com.evatool.application.service.impl;

import com.evatool.application.dto.VariantDto;
import com.evatool.application.mapper.VariantMapper;
import com.evatool.application.service.api.VariantService;
import com.evatool.domain.entity.Variant;
import com.evatool.domain.repository.VariantRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VariantServiceImpl extends CrudServiceImpl<Variant, VariantDto> implements VariantService {

    private static final Logger logger = LoggerFactory.getLogger(VariantServiceImpl.class);

    @Getter
    private final VariantRepository repository;

    @Getter
    private final VariantMapper mapper;

    public VariantServiceImpl(VariantRepository repository, VariantMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
