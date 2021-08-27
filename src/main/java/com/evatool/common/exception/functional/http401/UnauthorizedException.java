package com.evatool.common.exception.functional.http401;

import com.evatool.common.exception.functional.FunctionalException;
import com.evatool.common.exception.functional.Tag;
import org.springframework.http.HttpStatus;

public abstract class UnauthorizedException extends FunctionalException {
    protected UnauthorizedException(String message, int functionalErrorCode, Tag tag) {
        super(message, HttpStatus.UNAUTHORIZED, functionalErrorCode, tag);
    }
}
