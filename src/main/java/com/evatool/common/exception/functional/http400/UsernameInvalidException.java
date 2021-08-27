package com.evatool.common.exception.functional.http400;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UsernameInvalidException extends BadRequestException {
    public UsernameInvalidException(String message, String username) {
        super(message, FunctionalErrorCodesUtil.USERNAME_INVALID, new UsernameInvalidTag(username));
    }

    @Getter
    @RequiredArgsConstructor
    public static class UsernameInvalidTag extends Tag {
        private final String username;
    }
}
