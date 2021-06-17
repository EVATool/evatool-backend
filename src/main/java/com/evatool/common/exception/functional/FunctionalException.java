package com.evatool.common.exception.functional;

import lombok.Getter;

abstract public class FunctionalException extends RuntimeException {

    @Getter
    private final int functionalErrorCode;

    public FunctionalException(String message, int functionalErrorCode) {
        super(message);
        this.functionalErrorCode = functionalErrorCode;
    }
}
