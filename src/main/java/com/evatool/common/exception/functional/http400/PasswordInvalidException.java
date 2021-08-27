package com.evatool.common.exception.functional.http400;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PasswordInvalidException extends BadRequestException {
    public PasswordInvalidException(String message, String password) {
        super(message, FunctionalErrorCodesUtil.PASSWORD_EMPTY_OR_NULL, new PasswordInvalidTag(password));
    }

    @Getter
    @RequiredArgsConstructor
    public static class PasswordInvalidTag extends Tag {
        private final String password;
    }
}
