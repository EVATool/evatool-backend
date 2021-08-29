package com.evatool.common.exception.prevent;

import org.springframework.http.HttpStatus;

public class PropertyCannotBeUpdatedException extends PreventException {
    public PropertyCannotBeUpdatedException(String entityClass, String property) {
        super(String.format("Property '%s' of '%s' cannot be updated", property, entityClass), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
