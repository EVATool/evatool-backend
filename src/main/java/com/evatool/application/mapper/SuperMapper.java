package com.evatool.application.mapper;

import com.evatool.application.dto.SuperDto;
import com.evatool.common.exception.EntityNotFoundException;
import com.evatool.domain.entity.SuperEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public abstract class SuperMapper<S extends SuperEntity, T extends SuperDto> {

    private static final Logger logger = LoggerFactory.getLogger(SuperMapper.class);

    public abstract T toDto(S entity);

    public abstract S fromDto(T dto);

    public Iterable<T> toDtoList(Iterable<S> entityList) {
        logger.debug("To Dto List");
        var dtoList = new ArrayList<T>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    protected void amendToDto(S entity, T dto) {
        logger.debug("Amend To Dto");
        dto.setId(entity.getId());
    }

    protected void amendFromDto(S entity, T dto) {
        logger.debug("Amend From Dto");
        entity.setId(dto.getId());
    }

    public <O> O findByIdOrThrowIfEmpty(CrudRepository<O, UUID> repository, UUID id) {
        logger.debug("Find By Id Or Throw If Empty");
        Optional<O> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(repository.getClass().getSimpleName().replace("Repository", ""), id);
        }
        return optional.get();
    }

    protected <O extends SuperEntity> UUID[] getSuperEntityIds(Set<O> entitySet) {
        logger.debug("Get Super Entity Ids");
        var ids = new UUID[entitySet.size()];
        var index = 0;
        for (var o : entitySet) {
            ids[index] = o.getId();
            index++;
        }
        return ids;
    }

    protected Class<S> getEntityClass() {
        return (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
