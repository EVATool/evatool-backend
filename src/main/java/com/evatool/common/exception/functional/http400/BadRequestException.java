package com.evatool.common.exception.functional.http400;

import com.evatool.common.exception.functional.FunctionalException;
import com.evatool.common.exception.functional.Tag;
import org.springframework.http.HttpStatus;

public class BadRequestException extends FunctionalException {
    protected BadRequestException(String message, int functionalErrorCode, Tag tag) {
        super(message, HttpStatus.BAD_REQUEST, functionalErrorCode, tag);
    }
}
