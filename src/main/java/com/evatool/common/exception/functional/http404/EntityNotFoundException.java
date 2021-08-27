package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class EntityNotFoundException extends NotFoundException {
    public EntityNotFoundException(String entityClass, UUID id) {
        super(String.format("Entity '%s' with id '%s' not found", entityClass, id), FunctionalErrorCodesUtil.ENTITY_NOT_FOUND, new EntityNotFoundTag(entityClass, id));
    }

    @Getter
    @RequiredArgsConstructor
    public static class EntityNotFoundTag extends Tag {
        private final String entityClass;
        private final UUID id;
        private final int entityCode = 0; // TODO entity code for translation of entity name.
    }
}
