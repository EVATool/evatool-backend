package com.evatool.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class HttpStatusException extends RuntimeException {
    @Getter
    private HttpStatus httpStatus;

    protected HttpStatusException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
