package com.evatool.common.exception.functional.http401;

import com.evatool.common.util.FunctionalErrorCodesUtil;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException() {
        super("Invalid credentials", FunctionalErrorCodesUtil.INVALID_CREDENTIALS, null);
    }
}
