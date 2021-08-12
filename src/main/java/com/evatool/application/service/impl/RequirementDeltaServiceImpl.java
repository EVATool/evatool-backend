package com.evatool.application.service.impl;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.application.mapper.RequirementDeltaMapper;
import com.evatool.application.service.api.RequirementDeltaService;
import com.evatool.domain.entity.RequirementDelta;
import com.evatool.domain.repository.RequirementDeltaRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequirementDeltaServiceImpl extends CrudServiceImpl<RequirementDelta, RequirementDeltaDto> implements RequirementDeltaService {

    private static final Logger logger = LoggerFactory.getLogger(RequirementDeltaServiceImpl.class);

    @Getter
    private final RequirementDeltaRepository repository;

    @Getter
    private final RequirementDeltaMapper mapper;

    protected RequirementDeltaServiceImpl(RequirementDeltaRepository repository, RequirementDeltaMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
