package com.evatool.application.service.impl;

import com.evatool.application.dto.ImpactDto;
import com.evatool.application.mapper.ImpactMapper;
import com.evatool.application.service.api.ImpactService;
import com.evatool.common.exception.functional.EntityStillReferencedException;
import com.evatool.common.util.Util;
import com.evatool.domain.entity.Impact;
import com.evatool.domain.repository.ImpactRepository;
import com.evatool.domain.repository.RequirementDeltaRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.evatool.application.service.FunctionalErrorCodes.IMPACT_REFERENCED_BY_REQUIREMENT_DELTA;

@Service
public class ImpactServiceImpl extends CrudServiceImpl<Impact, ImpactDto> implements ImpactService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactServiceImpl.class);

    @Getter
    private final ImpactRepository repository;

    @Getter
    private final ImpactMapper mapper;

    @Getter
    private final RequirementDeltaRepository requirementDeltaRepository;

    public ImpactServiceImpl(ImpactRepository repository, ImpactMapper mapper, RequirementDeltaRepository requirementDeltaRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.requirementDeltaRepository = requirementDeltaRepository;
    }

    @Override
    public void deleteById(UUID id) {
        var referencedRequirementDeltas = requirementDeltaRepository.findAllByImpactId(id);
        if (Util.iterableSize(referencedRequirementDeltas) > 0) {
            var deltaIds = Util.entitySetToIdArray(referencedRequirementDeltas);
            var tag = "null";
            throw new EntityStillReferencedException("This impact is still referenced by a requirement delta",
                    IMPACT_REFERENCED_BY_REQUIREMENT_DELTA,
                    id);
        }
        super.deleteById(id);
    }
}
