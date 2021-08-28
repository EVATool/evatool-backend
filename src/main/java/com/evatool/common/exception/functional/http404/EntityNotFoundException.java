package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public class EntityNotFoundException extends NotFoundException {
    public EntityNotFoundException(String entityClass, UUID id, int functionalErrorCode) {
        super(String.format("Entity '%s' with id '%s' not found", entityClass, id), functionalErrorCode, new EntityNotFoundTag(id));
    }

    @Getter
    @RequiredArgsConstructor
    public static class EntityNotFoundTag extends Tag {
        private final UUID id;
    }
}
