package com.evatool.application.service.impl;

import com.evatool.application.dto.ImpactDto;
import com.evatool.application.mapper.ImpactDtoMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.ImpactService;
import com.evatool.common.exception.functional.EntityStillReferencedException;
import com.evatool.common.exception.functional.tag.ImpactReferencedByRequirements;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.Impact;
import com.evatool.domain.entity.Requirement;
import com.evatool.domain.repository.ImpactRepository;
import com.evatool.domain.repository.RequirementDeltaRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

import static com.evatool.application.service.FunctionalErrorCodes.IMPACT_REFERENCED_BY_REQUIREMENT_DELTA;

@Service
public class ImpactServiceImpl extends CrudServiceImpl<Impact, ImpactDto> implements ImpactService {

    private static final Logger logger = LoggerFactory.getLogger(ImpactServiceImpl.class);

    @Getter
    private final ImpactRepository repository;

    @Getter
    private final ImpactDtoMapper mapper;

    @Getter
    private final RequirementDeltaRepository requirementDeltaRepository;

    public ImpactServiceImpl(ImpactRepository repository, ImpactDtoMapper mapper, RequirementDeltaRepository requirementDeltaRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.requirementDeltaRepository = requirementDeltaRepository;
    }

    @Override
    public void deleteById(UUID id) {
        var referencedRequirementDeltas = requirementDeltaRepository.findAllByImpactId(id);
        referencedRequirementDeltas = TenancySentinel.handleFind(referencedRequirementDeltas);

        if (IterableUtil.iterableSize(referencedRequirementDeltas) > 0) {
            var referencedRequirements = new ArrayList<Requirement>();
            referencedRequirementDeltas.forEach(delta -> referencedRequirements.add(delta.getRequirement()));

            var requirementIds = IterableUtil.entityIterableToIdArray(referencedRequirements);
            var requirementDeltaIds = IterableUtil.entityIterableToIdArray(referencedRequirementDeltas);

            var tag = new ImpactReferencedByRequirements(id, requirementIds, requirementDeltaIds);

            throw new EntityStillReferencedException("This impact is still referenced by a requirement delta",
                    IMPACT_REFERENCED_BY_REQUIREMENT_DELTA,
                    tag);
        }
        super.deleteById(id);
    }
}
