package com.evatool.common.exception.prevent;

import org.springframework.http.HttpStatus;

public class PropertyCannotBeNullException extends PreventException {
    public PropertyCannotBeNullException(String entityClass, String propertyName) {
        super(String.format("The property '%s' of '%s' cannot be null", propertyName, entityClass), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
