package com.evatool.common.exception.functional.http422;

public class PropertyMustBeNullException extends RuntimeException {
    public PropertyMustBeNullException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' must be null", property, entityClass));
    }
}
