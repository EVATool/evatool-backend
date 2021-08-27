package com.evatool.common.exception.functional.http422;

public class PropertyCannotBeNullException extends RuntimeException {
    public PropertyCannotBeNullException(String entityClass, String propertyName) {
        super(String.format("The property '%s' of '%s' cannot be null", propertyName, entityClass));
    }
}
