package com.evatool.common.exception;

public class PropertyCannotBeUpdatedException extends RuntimeException {
    public PropertyCannotBeUpdatedException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' cannot be updated", property, entityClass));
    }
}
