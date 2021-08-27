package com.evatool.common.exception.prevent.http422;

public class PropertyCannotBeUpdatedException extends UnprocessableEntityException {
    public PropertyCannotBeUpdatedException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' cannot be updated", property, entityClass));
    }
}
