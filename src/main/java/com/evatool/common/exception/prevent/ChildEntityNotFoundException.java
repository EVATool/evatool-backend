package com.evatool.common.exception.prevent;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ChildEntityNotFoundException extends PreventException {
    public ChildEntityNotFoundException(String entityClass, UUID id) {
        super(String.format("Entity '%s' with id '%s' not found", entityClass, id), HttpStatus.NOT_FOUND);
    }
}
