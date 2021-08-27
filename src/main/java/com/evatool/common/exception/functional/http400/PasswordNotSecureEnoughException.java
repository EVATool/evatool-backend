package com.evatool.common.exception.functional.http400;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PasswordNotSecureEnoughException extends BadRequestException {
    public PasswordNotSecureEnoughException(String message, String password) {
        super(message, FunctionalErrorCodesUtil.PASSWORD_NOT_SECURE_ENOUGH, new PasswordNotSecureEnoughTag(password));
    }

    @Getter
    @RequiredArgsConstructor
    public static class PasswordNotSecureEnoughTag extends Tag {
        private final String password;
    }
}
