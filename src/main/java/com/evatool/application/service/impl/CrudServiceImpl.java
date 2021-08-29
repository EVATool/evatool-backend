package com.evatool.application.service.impl;

import com.evatool.application.dto.SuperDto;
import com.evatool.application.mapper.SuperMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.CrudService;
import com.evatool.common.exception.functional.http404.EntityNotFoundException;
import com.evatool.common.exception.prevent.PropertyCannotBeNullException;
import com.evatool.common.exception.prevent.PropertyMustBeNullException;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import com.evatool.domain.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public abstract class CrudServiceImpl<S extends SuperEntity, T extends SuperDto> implements CrudService<T> {

    private static final Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);

    protected final CrudRepository<S, UUID> crudRepository;

    protected final SuperMapper<S, T> baseMapper;

    protected CrudServiceImpl(CrudRepository<S, UUID> crudRepository, SuperMapper<S, T> baseMapper) {
        this.crudRepository = crudRepository;
        this.baseMapper = baseMapper;
    }

    @Override
    public T findById(UUID id) {
        logger.debug("Find By Id");
        if (id == null) {
            throw new PropertyCannotBeNullException(getEntityClass().getSimpleName(), "id");
        }
        var optional = crudRepository.findById(id);
        if (optional.isEmpty()) {
            var functionalErrorCode = getNotFoundInFindErrorCode(getEntityClass());
            throw new EntityNotFoundException(getClass().getSimpleName(), id, functionalErrorCode);
        }
        var entity = optional.get();
        TenancySentinel.handleFind(entity);
        return baseMapper.toDto(entity);
    }

    @Override
    public Iterable<T> findAll() {
        logger.debug("Find All");
        List<T> dtoList = new ArrayList<>();
        var entities = crudRepository.findAll();
        entities = TenancySentinel.handleFind(entities);
        for (var entity : entities) {
            dtoList.add(baseMapper.toDto(entity));
        }
        return dtoList;
    }

    @Override
    public T create(T dto) {
        logger.debug("Create");
        if (dto.getId() != null) {
            throw new PropertyMustBeNullException(getDtoClass().getSimpleName(), "id");
        }
        var entity = baseMapper.fromDto(dto);
        TenancySentinel.handleCreate(entity);
        entity = crudRepository.save(entity);
        return baseMapper.toDto(entity);
    }

    @Override
    public T update(T dto) {
        logger.debug("Update");
        if (dto.getId() == null) {
            throw new PropertyCannotBeNullException(getEntityClass().getSimpleName(), "id");
        }
        var optional = crudRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            var functionalErrorCode = getNotFoundInUpdateErrorCode(getEntityClass());
            throw new EntityNotFoundException(getClass().getSimpleName(), dto.getId(), functionalErrorCode);
        }
        var foundEntity = optional.get();
        var entity = baseMapper.fromDto(dto);
        entity.setRealm(foundEntity.getRealm());
        TenancySentinel.handleUpdate(entity);
        entity = crudRepository.save(entity);
        return baseMapper.toDto(entity);
    }

    @Override
    public void deleteById(UUID id) {
        logger.debug("Delete");
        if (id == null) {
            throw new PropertyCannotBeNullException(getEntityClass().getSimpleName(), "id");
        }
        var optional = crudRepository.findById(id);
        if (optional.isEmpty()) {
            var functionalErrorCode = getNotFoundInDeleteErrorCode(getEntityClass());
            throw new EntityNotFoundException(getClass().getSimpleName(), id, functionalErrorCode);
        }
        var entity = optional.get();
        TenancySentinel.handleDelete(entity);
        crudRepository.deleteById(id);
    }

    public void deleteAll() {
        logger.debug("Delete All");
        var entities = crudRepository.findAll();
        entities = TenancySentinel.handleFind(entities);
        crudRepository.deleteAll(entities);
    }

    protected Class<S> getEntityClass() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getDtoClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    private int getNotFoundInFindErrorCode(Class<S> type) {
        if (type == Analysis.class) {
            return FunctionalErrorCodesUtil.ANALYSIS_FIND_FAILED_NOT_FOUND;
        } else if (type == Impact.class) {
            return FunctionalErrorCodesUtil.IMPACT_FIND_FAILED_NOT_FOUND;
        } else if (type == Requirement.class) {
            return FunctionalErrorCodesUtil.REQUIREMENT_FIND_FAILED_NOT_FOUND;
        } else if (type == RequirementDelta.class) {
            return FunctionalErrorCodesUtil.REQUIREMENT_DELTA_FIND_FAILED_NOT_FOUND;
        } else if (type == Stakeholder.class) {
            return FunctionalErrorCodesUtil.STAKEHOLDER_FIND_FAILED_NOT_FOUND;
        } else if (type == Value.class) {
            return FunctionalErrorCodesUtil.VALUE_FIND_FAILED_NOT_FOUND;
        } else if (type == Variant.class) {
            return FunctionalErrorCodesUtil.VARIANT_FIND_FAILED_NOT_FOUND;
        } else {
            throw new IllegalArgumentException("No 'NotFoundInFind' functional error code found for type " + type.getSimpleName());
        }
    }

    private int getNotFoundInUpdateErrorCode(Class<S> type) {
        if (type == Analysis.class) {
            return FunctionalErrorCodesUtil.ANALYSIS_UPDATE_FAILED_NOT_FOUND;
        } else if (type == Impact.class) {
            return FunctionalErrorCodesUtil.IMPACT_UPDATE_FAILED_NOT_FOUND;
        } else if (type == Requirement.class) {
            return FunctionalErrorCodesUtil.REQUIREMENT_UPDATE_FAILED_NOT_FOUND;
        } else if (type == RequirementDelta.class) {
            return FunctionalErrorCodesUtil.REQUIREMENT_DELTA_UPDATE_FAILED_NOT_FOUND;
        } else if (type == Stakeholder.class) {
            return FunctionalErrorCodesUtil.STAKEHOLDER_UPDATE_FAILED_NOT_FOUND;
        } else if (type == Value.class) {
            return FunctionalErrorCodesUtil.VALUE_UPDATE_FAILED_NOT_FOUND;
        } else if (type == Variant.class) {
            return FunctionalErrorCodesUtil.VARIANT_UPDATE_FAILED_NOT_FOUND;
        } else {
            throw new IllegalArgumentException("No 'NotFoundInUpdate' functional error code found for type " + type.getSimpleName());
        }
    }

    private int getNotFoundInDeleteErrorCode(Class<S> type) {
        if (type == Analysis.class) {
            return FunctionalErrorCodesUtil.ANALYSIS_DELETION_FAILED_NOT_FOUND;
        } else if (type == Impact.class) {
            return FunctionalErrorCodesUtil.IMPACT_DELETION_FAILED_NOT_FOUND;
        } else if (type == Requirement.class) {
            return FunctionalErrorCodesUtil.REQUIREMENT_DELETION_FAILED_NOT_FOUND;
        } else if (type == RequirementDelta.class) {
            return FunctionalErrorCodesUtil.REQUIREMENT_DELTA_DELETION_FAILED_NOT_FOUND;
        } else if (type == Stakeholder.class) {
            return FunctionalErrorCodesUtil.STAKEHOLDER_DELETION_FAILED_NOT_FOUND;
        } else if (type == Value.class) {
            return FunctionalErrorCodesUtil.VALUE_DELETION_FAILED_NOT_FOUND;
        } else if (type == Variant.class) {
            return FunctionalErrorCodesUtil.VARIANT_DELETION_FAILED_NOT_FOUND;
        } else {
            throw new IllegalArgumentException("No 'NotFoundInDelete' functional error code found for type " + type.getSimpleName());
        }
    }
}
