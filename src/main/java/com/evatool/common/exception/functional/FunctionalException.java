package com.evatool.common.exception.functional;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class FunctionalException extends RuntimeException {

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private final int functionalErrorCode;

    @Getter
    private final Object tag;

    protected FunctionalException(String message, HttpStatus httpStatus, int functionalErrorCode, Object tag) {
        super(message);
        this.httpStatus = httpStatus;
        this.functionalErrorCode = functionalErrorCode;
        this.tag = tag;
    }
}
