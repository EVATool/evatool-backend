package com.evatool.common.exception;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass, UUID id) {
        this(String.format("The entity of type '%s' with id '%s' was not found", entityClass.getSimpleName(), id == null ? "null" : id.toString()));
    }

    public EntityNotFoundException(String entityClass, UUID id) {
        this(String.format("The entity of type '%s' with id '%s' was not found", entityClass, id == null ? "null" : id.toString()));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
