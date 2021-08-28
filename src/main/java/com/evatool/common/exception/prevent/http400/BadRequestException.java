package com.evatool.common.exception.prevent.http400;

import com.evatool.common.exception.prevent.PreventException;
import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends PreventException {
    protected BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
