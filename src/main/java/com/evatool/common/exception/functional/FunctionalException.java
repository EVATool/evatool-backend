package com.evatool.common.exception.functional;

import com.evatool.common.exception.HttpStatusException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// A FunctionalException should be used to create meaningful error messages for the end-user.
public abstract class FunctionalException extends HttpStatusException {

    @Getter
    private final int functionalErrorCode;

    @Getter
    private final Tag tag;

    protected FunctionalException(String message, HttpStatus httpStatus, int functionalErrorCode, Tag tag) {
        super(message, httpStatus);
        this.functionalErrorCode = functionalErrorCode;
        this.tag = tag;
    }
}
