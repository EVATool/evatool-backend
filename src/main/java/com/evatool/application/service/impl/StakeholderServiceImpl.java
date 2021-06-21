package com.evatool.application.service.impl;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.application.mapper.StakeholderMapper;
import com.evatool.application.service.api.StakeholderService;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import com.evatool.common.exception.functional.EntityStillReferencedException;
import com.evatool.common.exception.functional.tag.StakeholderReferencedByImpact;
import com.evatool.common.util.Util;
import com.evatool.domain.entity.Stakeholder;
import com.evatool.domain.repository.ImpactRepository;
import com.evatool.domain.repository.StakeholderRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

import static com.evatool.application.service.FunctionalErrorCodes.STAKEHOLDER_REFERENCED_BY_IMPACT;

@Service
public class StakeholderServiceImpl extends CrudServiceImpl<Stakeholder, StakeholderDto> implements StakeholderService {

    private static final Logger logger = LoggerFactory.getLogger(StakeholderServiceImpl.class);

    @Getter
    private final StakeholderRepository repository;

    @Getter
    private final StakeholderMapper mapper;

    @Getter
    private final ImpactRepository impactRepository;

    public StakeholderServiceImpl(StakeholderRepository repository, StakeholderMapper mapper, ImpactRepository impactRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.impactRepository = impactRepository;
    }

    @Override
    public void deleteById(UUID id) {
        var referencedImpacts = impactRepository.findAllByStakeholderId(id);
        if (Util.iterableSize(referencedImpacts) > 0) {
            var impactIds = Util.entityIterableToIdArray(referencedImpacts);

            var tag = new StakeholderReferencedByImpact(id, impactIds);

            throw new EntityStillReferencedException("This stakeholder is still referenced by an impact",
                    STAKEHOLDER_REFERENCED_BY_IMPACT,
                    tag);
        }
        super.deleteById(id);
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
