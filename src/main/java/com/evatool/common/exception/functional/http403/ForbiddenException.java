package com.evatool.common.exception.functional.http403;

import com.evatool.common.exception.functional.FunctionalException;
import com.evatool.common.exception.functional.Tag;
import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends FunctionalException {
    protected ForbiddenException(String message, int functionalErrorCode, Tag tag) {
        super(message, HttpStatus.FORBIDDEN, functionalErrorCode, tag);
    }
}
