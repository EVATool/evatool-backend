package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

public abstract class EntityNotFoundException extends NotFoundException {
    protected EntityNotFoundException(String entityClass, UUID id, int functionalErrorCode, Tag tag) {
        super(String.format("Entity '%s' with id '%s' not found", entityClass, id), functionalErrorCode, tag);
    }

    @Getter
    @RequiredArgsConstructor
    public static class EntityNotFoundTag extends Tag {
        private final UUID id;
    }
}
