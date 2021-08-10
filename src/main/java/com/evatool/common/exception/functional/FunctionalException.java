package com.evatool.common.exception.functional;

import lombok.Getter;

abstract public class FunctionalException extends RuntimeException {

    @Getter
    private final int functionalErrorCode;

    @Getter
    private final Object tag;

    public FunctionalException(String message, int functionalErrorCode, Object tag) {
        super(message);
        this.functionalErrorCode = functionalErrorCode;
        this.tag = tag;
    }
}
