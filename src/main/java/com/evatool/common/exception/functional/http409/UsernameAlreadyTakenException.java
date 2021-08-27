package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UsernameAlreadyTakenException extends ConflictException {
    public UsernameAlreadyTakenException(String username) {
        super(username, FunctionalErrorCodesUtil.REGISTER_USERNAME_ALREADY_EXISTS, new UsernameAlreadyTakenTag(username));
    }

    @Getter
    @RequiredArgsConstructor
    public static class UsernameAlreadyTakenTag extends Tag {
        public final String username;
    }
}
