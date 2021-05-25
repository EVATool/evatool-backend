package com.evatool.application.service.impl;

import com.evatool.application.dto.RequirementDto;
import com.evatool.application.mapper.RequirementMapper;
import com.evatool.application.service.api.RequirementService;
import com.evatool.domain.entity.Requirement;
import com.evatool.domain.repository.RequirementRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequirementServiceImpl extends CrudServiceImpl<Requirement, RequirementDto> implements RequirementService {

    private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImpl.class);

    @Getter
    private final RequirementRepository repository;

    @Getter
    private final RequirementMapper mapper;

    public RequirementServiceImpl(RequirementRepository repository, RequirementMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
