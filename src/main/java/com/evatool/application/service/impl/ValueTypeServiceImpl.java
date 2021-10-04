package com.evatool.application.service.impl;

import com.evatool.application.dto.ValueTypeDto;
import com.evatool.application.mapper.ValueTypeMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.ValueTypeService;
import com.evatool.common.exception.functional.http409.EntityStillReferencedException;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.ValueType;
import com.evatool.domain.repository.ValueRepository;
import com.evatool.domain.repository.ValueTypeRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.evatool.common.util.FunctionalErrorCodesUtil.VARIANT_TYPE_REFERENCED_BY_VARIANT;

@Service
public class ValueTypeServiceImpl extends CrudServiceImpl<ValueType, ValueTypeDto> implements ValueTypeService {

    private static final Logger logger = LoggerFactory.getLogger(ValueTypeServiceImpl.class);

    @Getter
    private final ValueTypeRepository repository;

    @Getter
    private final ValueTypeMapper mapper;

    @Getter
    private final ValueRepository valueRepository;

    public ValueTypeServiceImpl(ValueTypeRepository repository, ValueTypeMapper mapper, ValueRepository valueRepository) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
        this.valueRepository = valueRepository;
    }

    @Override
    public void deleteById(UUID id) {
        logger.trace("Delete By Id");

        var referencedValues = valueRepository.findAllByValueTypeId(id);
        referencedValues = TenancySentinel.handleFind(referencedValues);

        if (IterableUtil.iterableSize(referencedValues) > 0) {
            var valuesIds = IterableUtil.entityIterableToIdArray(referencedValues);

            var tag = new EntityStillReferencedException.ValueTypeReferencedByValuesTag(id, valuesIds);

            throw new EntityStillReferencedException("This value type is still referenced by a value",
                    VARIANT_TYPE_REFERENCED_BY_VARIANT,
                    tag);
        }
        super.deleteById(id);
    }
}
