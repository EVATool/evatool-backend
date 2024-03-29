package com.evatool.application.service.impl;

import com.evatool.application.dto.VariantDto;
import com.evatool.application.mapper.VariantMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.VariantService;
import com.evatool.common.exception.functional.http409.EntityStillReferencedException;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.Variant;
import com.evatool.domain.repository.RequirementRepository;
import com.evatool.domain.repository.VariantRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.evatool.common.util.FunctionalErrorCodesUtil.VARIANT_REFERENCED_BY_REQUIREMENT;

@Service
public class VariantServiceImpl extends CrudServiceImpl<Variant, VariantDto> implements VariantService {

    private static final Logger logger = LoggerFactory.getLogger(VariantServiceImpl.class);

    @Getter
    private final VariantRepository repository;

    @Getter
    private final VariantMapper mapper;

    @Getter
    private final RequirementRepository requirementRepository;

    public VariantServiceImpl(VariantRepository repository, VariantMapper mapper, RequirementRepository requirementRepository) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
        this.requirementRepository = requirementRepository;
    }

    @Override
    public void deleteById(UUID id) {
        logger.trace("Delete By Id");

        var referencedRequirements = requirementRepository.findAllByVariantsId(id);
        referencedRequirements = TenancySentinel.handleFind(referencedRequirements);

        if (IterableUtil.iterableSize(referencedRequirements) > 0) {
            var requirementIds = IterableUtil.entityIterableToIdArray(referencedRequirements);

            var tag = new EntityStillReferencedException.VariantReferencedByRequirementsTag(id, requirementIds);

            throw new EntityStillReferencedException("This variant is still referenced by a requirement",
                    VARIANT_REFERENCED_BY_REQUIREMENT,
                    tag);

        }
        super.deleteById(id);
    }
}
