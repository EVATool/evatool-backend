package com.evatool.application.service.impl;

import com.evatool.application.dto.ImpactDto;
import com.evatool.application.mapper.ImpactMapper;
import com.evatool.application.service.api.ImpactService;
import com.evatool.domain.entity.Impact;
import com.evatool.domain.repository.ImpactRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImpactServiceImpl extends CrudServiceImpl<Impact, ImpactDto> implements ImpactService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactServiceImpl.class);

    @Getter
    private final ImpactRepository repository;

    @Getter
    private final ImpactMapper mapper;

    public ImpactServiceImpl(ImpactRepository repository, ImpactMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
