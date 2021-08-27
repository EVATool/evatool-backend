package com.evatool.common.exception.prevent.http422;

public class PropertyCannotBeNullException extends UnprocessableEntityException {
    public PropertyCannotBeNullException(String entityClass, String propertyName) {
        super(String.format("The property '%s' of '%s' cannot be null", propertyName, entityClass));
    }
}
