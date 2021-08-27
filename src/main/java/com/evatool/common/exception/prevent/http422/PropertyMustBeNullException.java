package com.evatool.common.exception.prevent.http422;

public class PropertyMustBeNullException extends UnprocessableEntityException {
    public PropertyMustBeNullException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' must be null", property, entityClass));
    }
}
