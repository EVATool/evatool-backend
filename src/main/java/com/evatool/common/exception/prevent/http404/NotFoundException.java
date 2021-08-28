package com.evatool.common.exception.prevent.http404;

import com.evatool.common.exception.prevent.PreventException;
import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends PreventException {
    protected NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
