package com.evatool.application.service.impl;

import com.evatool.application.dto.VariantTypeDto;
import com.evatool.application.mapper.VariantTypeMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.VariantTypeService;
import com.evatool.common.exception.functional.http409.EntityStillReferencedException;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.VariantType;
import com.evatool.domain.repository.VariantRepository;
import com.evatool.domain.repository.VariantTypeRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.evatool.common.util.FunctionalErrorCodesUtil.*;

@Service
public class VariantTypeServiceImpl extends CrudServiceImpl<VariantType, VariantTypeDto> implements VariantTypeService {

    private static final Logger logger = LoggerFactory.getLogger(VariantTypeServiceImpl.class);

    @Getter
    private final VariantTypeRepository repository;

    @Getter
    private final VariantTypeMapper mapper;

    @Getter
    private final VariantRepository variantRepository;

    public VariantTypeServiceImpl(VariantTypeRepository repository, VariantTypeMapper mapper, VariantRepository variantRepository) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
        this.variantRepository = variantRepository;
    }

    @Override
    public void deleteById(UUID id) {
        logger.trace("Delete By Id");

        var referencedVariants = variantRepository.findAllByVariantTypeId(id);
        referencedVariants = TenancySentinel.handleFind(referencedVariants);

        if (IterableUtil.iterableSize(referencedVariants) > 0) {
            var variantsIds = IterableUtil.entityIterableToIdArray(referencedVariants);

            var tag = new EntityStillReferencedException.VariantTypeReferencedByVariantsTag(id, variantsIds);

            throw new EntityStillReferencedException("This variant type is still referenced by a variant",
                    VARIANT_TYPE_REFERENCED_BY_VARIANT,
                    tag);
        }
        super.deleteById(id);
    }
}