package com.evatool.common.exception.functional.http400;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class EmailInvalidException extends BadRequestException {
    public EmailInvalidException(String message, String email) {
        super(message, FunctionalErrorCodesUtil.EMAIL_INVALID, new EmailInvalidTag(email));
    }

    @Getter
    @RequiredArgsConstructor
    public static class EmailInvalidTag extends Tag {
        private final String email;
    }
}
