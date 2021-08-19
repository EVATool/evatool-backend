package com.evatool.common.exception.functional;

import lombok.Getter;

public abstract class FunctionalException extends RuntimeException {

    @Getter
    private final int functionalErrorCode;

    @Getter
    private final Object tag;

    protected FunctionalException(String message, int functionalErrorCode, Object tag) {
        super(message);
        this.functionalErrorCode = functionalErrorCode;
        this.tag = tag;
    }
}
