package com.evatool.common.exception.prevent.http400;

public class PasswordEmptyOrNullException extends BadRequestException {
    public PasswordEmptyOrNullException(String message, String password) {
        super(message);
    }
}
