package com.evatool.common.exception;

public class PropertyCannotBeNullException extends RuntimeException {
    public PropertyCannotBeNullException(Class<?> entityClass, String propertyName) {
        super(String.format("The property '%s' of '%s' cannot be null", propertyName, entityClass.getSimpleName()));
    }
}
