package com.evatool.common.exception.functional.http422;

public class PropertyCannotBeUpdatedException extends RuntimeException {
    public PropertyCannotBeUpdatedException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' cannot be updated", property, entityClass));
    }
}
