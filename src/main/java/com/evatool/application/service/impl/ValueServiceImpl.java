package com.evatool.application.service.impl;

import com.evatool.application.dto.ValueDto;
import com.evatool.application.mapper.ValueMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.ValueService;
import com.evatool.common.exception.functional.http409.EntityStillReferencedException;
import com.evatool.common.util.IterableUtil;
import com.evatool.domain.entity.Value;
import com.evatool.domain.repository.ImpactRepository;
import com.evatool.domain.repository.ValueRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.evatool.common.util.FunctionalErrorCodesUtil.VALUE_REFERENCED_BY_IMPACT;

@Service
public class ValueServiceImpl extends CrudServiceImpl<Value, ValueDto> implements ValueService {

    private static final Logger logger = LoggerFactory.getLogger(ValueServiceImpl.class);

    @Getter
    private final ValueRepository repository;

    @Getter
    private final ValueMapper mapper;

    @Getter
    private final ImpactRepository impactRepository;

    public ValueServiceImpl(ValueRepository repository, ValueMapper mapper, ImpactRepository impactRepository) {
        super(repository, mapper);
        logger.trace("Constructor");
        this.repository = repository;
        this.mapper = mapper;
        this.impactRepository = impactRepository;
    }

    @Override
    public void deleteById(UUID id) {
        logger.trace("Delete By Id");

        var referencedImpacts = impactRepository.findAllByValueId(id);
        referencedImpacts = TenancySentinel.handleFind(referencedImpacts);

        if (IterableUtil.iterableSize(referencedImpacts) > 0) {
            var impactIds = IterableUtil.entityIterableToIdArray(referencedImpacts);

            var tag = new EntityStillReferencedException.ValueReferencedByImpactsTag(id, impactIds);

            throw new EntityStillReferencedException("This value is still referenced by an impact",
                    VALUE_REFERENCED_BY_IMPACT,
                    tag);
        }
        super.deleteById(id);
    }
}
