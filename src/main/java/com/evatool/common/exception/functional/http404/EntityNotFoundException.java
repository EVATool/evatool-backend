package com.evatool.common.exception.functional.http404;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityClass, UUID id) {
        this(entityClass, id, "id");
    }

    public EntityNotFoundException(String entityClass, UUID id, String field) {
        this(entityClass, id == null ? "null" : id.toString(), field);
    }

    public EntityNotFoundException(String entityClass, String id, String field) {
        super(String.format("The entity of type '%s' with %s '%s' was not found", entityClass, field, id));
    }
}
