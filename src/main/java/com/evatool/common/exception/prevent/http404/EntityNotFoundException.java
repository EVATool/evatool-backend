package com.evatool.common.exception.prevent.http404;

import java.util.UUID;

public class EntityNotFoundException extends NotFoundException {
    public EntityNotFoundException(String entityClass, UUID id) {
        super(String.format("Entity '%s' with id '%s' not found", entityClass, id));
    }
}
