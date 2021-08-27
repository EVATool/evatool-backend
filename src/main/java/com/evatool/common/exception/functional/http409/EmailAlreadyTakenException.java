package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class EmailAlreadyTakenException extends ConflictException {
    public EmailAlreadyTakenException(String email) {
        super(email, FunctionalErrorCodesUtil.REGISTER_USERNAME_ALREADY_EXISTS, new EmailAlreadyTaken(email));
    }

    @Getter
    @RequiredArgsConstructor
    public static class EmailAlreadyTaken extends Tag {
        public final String username;
    }
}
