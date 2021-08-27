package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.FunctionalException;
import com.evatool.common.exception.functional.Tag;
import org.springframework.http.HttpStatus;

public class NotFoundException extends FunctionalException {
    protected NotFoundException(String message, int functionalErrorCode, Tag tag) {
        super(message, HttpStatus.NOT_FOUND, functionalErrorCode, tag);
    }
}
