package com.evatool.common.exception.prevent.http422;

public class PropertyIsInvalidException extends UnprocessableEntityException {
    public PropertyIsInvalidException(String message) {
        super(message);
    }
}
