package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.FunctionalException;
import com.evatool.common.exception.functional.Tag;
import org.springframework.http.HttpStatus;

public abstract class ConflictException extends FunctionalException {
    protected ConflictException(String message, int functionalErrorCode, Tag tag) {
        super(message, HttpStatus.CONFLICT, functionalErrorCode, tag);
    }
}
