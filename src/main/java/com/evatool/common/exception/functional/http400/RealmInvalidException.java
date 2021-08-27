package com.evatool.common.exception.functional.http400;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RealmInvalidException extends BadRequestException {
    public RealmInvalidException(String message, String realm) {
        super(message, FunctionalErrorCodesUtil.REALM_INVALID, new RealmInvalidTag(realm));
    }

    @Getter
    @RequiredArgsConstructor
    public static class RealmInvalidTag extends Tag {
        private final String realm;
    }
}
