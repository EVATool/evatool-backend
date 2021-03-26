package com.evatool.requirements.common.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public static final String MESSAGE_FORMAT = "'%s' with id '%s' was not found.";

    public EntityNotFoundException(Class<?> c, UUID id) {
        super(String.format(MESSAGE_FORMAT, c.getSimpleName(), id.toString()));
    }
}
