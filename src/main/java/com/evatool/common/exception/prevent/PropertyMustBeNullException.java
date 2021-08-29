package com.evatool.common.exception.prevent;

import org.springframework.http.HttpStatus;

public class PropertyMustBeNullException extends PreventException {
    public PropertyMustBeNullException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' must be null", property, entityClass), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
