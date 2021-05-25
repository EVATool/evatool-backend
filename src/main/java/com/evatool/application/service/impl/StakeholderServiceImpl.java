package com.evatool.application.service.impl;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.application.mapper.StakeholderMapper;
import com.evatool.application.service.api.StakeholderService;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.domain.entity.Stakeholder;
import com.evatool.domain.repository.StakeholderRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StakeholderServiceImpl extends CrudServiceImpl<Stakeholder, StakeholderDto> implements StakeholderService {

    private static final Logger logger = LoggerFactory.getLogger(StakeholderServiceImpl.class);

    @Getter
    private final StakeholderRepository repository;

    @Getter
    private final StakeholderMapper mapper;

    public StakeholderServiceImpl(StakeholderRepository repository, StakeholderMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Iterable<StakeholderLevel> findAllStakeholderLevels() {
        logger.debug("Find All Stakeholder Levels");
        return Arrays.asList(StakeholderLevel.values());
    }

    @Override
    public Iterable<StakeholderPriority> findAllStakeholderPriorities() {
        logger.debug("Find All Stakeholder Priorities");
        return Arrays.asList(StakeholderPriority.values());
    }
}
