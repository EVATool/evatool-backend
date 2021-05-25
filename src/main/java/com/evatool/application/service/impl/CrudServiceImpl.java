package com.evatool.application.service.impl;

import com.evatool.application.dto.SuperDto;
import com.evatool.application.mapper.SuperMapper;
import com.evatool.application.service.api.CrudService;
import com.evatool.common.exception.EntityNotFoundException;
import com.evatool.common.exception.PropertyCannotBeNullException;
import com.evatool.common.exception.PropertyMustBeNullException;
import com.evatool.domain.entity.SuperEntity;
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
            throw new PropertyCannotBeNullException(getEntityClass(), "id");
        }
        var optional = crudRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(getClass(), id);
        }
        var entity = optional.get();
        return baseMapper.toDto(entity);
    }

    @Override
    public Iterable<T> findAll() {
        logger.debug("Find All");
        List<T> dtoList = new ArrayList<>();
        for (var entity : crudRepository.findAll()) {
            dtoList.add(baseMapper.toDto(entity));
        }
        return dtoList;
    }

    @Override
    public T create(T dto) {
        logger.debug("Create");
        if (dto.getId() != null) {
            throw new PropertyMustBeNullException(getDtoClass(), "id");
        }
        var entity = baseMapper.fromDto(dto);
        entity = crudRepository.save(entity);
        return baseMapper.toDto(entity);
    }

    @Override
    public T update(T dto) {
        logger.debug("Update");
        if (dto.getId() == null) {
            throw new PropertyCannotBeNullException(getEntityClass(), "id");
        }
        var optional = crudRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(getClass(), dto.getId());
        }
        var entity = baseMapper.fromDto(dto);
        entity = crudRepository.save(entity);
        return baseMapper.toDto(entity);
    }

    @Override
    public void deleteById(UUID id) {
        logger.debug("Delete");
        if (id == null) {
            throw new PropertyCannotBeNullException(getEntityClass(), "id");
        }
        var optional = crudRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(getClass(), id);
        }
        crudRepository.deleteById(id);
    }

    public void deleteAll() {
        logger.debug("Delete All");
        crudRepository.deleteAll();
    }

    protected Class<S> getEntityClass() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getDtoClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
