package com.evatool.common.exception.prevent.http422;

import com.evatool.common.exception.prevent.PreventException;
import org.springframework.http.HttpStatus;

public abstract class UnprocessableEntityException extends PreventException {
    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
