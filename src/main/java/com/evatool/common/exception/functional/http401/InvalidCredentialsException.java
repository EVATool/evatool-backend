package com.evatool.common.exception.functional.http401;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException(int remainingLoginAttempts) {
        super("Invalid credentials", FunctionalErrorCodesUtil.INVALID_CREDENTIALS, new RemainingLoginAttemptsTag(remainingLoginAttempts));
    }

    @Getter
    @RequiredArgsConstructor
    public static class RemainingLoginAttemptsTag extends Tag {
        private final Integer remainingLoginAttempts;
    }
}
