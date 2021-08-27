package com.evatool.common.exception;

public class PropertyMustBeNullException extends RuntimeException {
    public PropertyMustBeNullException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' must be null", property, entityClass));
    }
}
