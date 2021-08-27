package com.evatool.common.exception.functional;

import org.springframework.http.HttpStatus;

public class ConflictException extends FunctionalException {
    protected ConflictException(String message, int functionalErrorCode, Tag tag) {
        super(message, HttpStatus.CONFLICT, functionalErrorCode, tag);
    }
}
