package com.evatool.common.exception.prevent;

import org.springframework.http.HttpStatus;

public class PropertyIsInvalidException extends PreventException {
    public PropertyIsInvalidException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
