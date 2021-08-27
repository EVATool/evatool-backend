package com.evatool.common.exception.prevent.http422;

import com.evatool.common.exception.prevent.PreventException;

public abstract class UnprocessableEntityException extends PreventException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
