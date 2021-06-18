package com.evatool.application.service.impl;

import com.evatool.application.dto.VariantDto;
import com.evatool.application.mapper.VariantMapper;
import com.evatool.application.service.api.VariantService;
import com.evatool.common.exception.functional.EntityStillReferencedException;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.Variant;
import com.evatool.domain.repository.RequirementRepository;
import com.evatool.domain.repository.VariantRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.evatool.application.service.FunctionalErrorCodes.VARIANT_REFERENCED_BY_REQUIREMENT;

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
        this.repository = repository;
        this.mapper = mapper;
        this.requirementRepository = requirementRepository;
    }

    @Override
    public void deleteById(UUID id) {
        if (IterableUtil.iterableSize(requirementRepository.findAllByVariantsId(id)) > 0) {
            throw new EntityStillReferencedException("This variant is still referenced by a requirement", VARIANT_REFERENCED_BY_REQUIREMENT, id);
        }
        super.deleteById(id);
    }
}
