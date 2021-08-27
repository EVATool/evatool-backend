package com.evatool.common.exception.functional.http409;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RealmAlreadyTakenException extends ConflictException {
    public RealmAlreadyTakenException(String realm) {
        super(realm, FunctionalErrorCodesUtil.REGISTER_REALM_ALREADY_EXISTS, new RealmAlreadyTakenTag(realm));
    }

    @Getter
    @RequiredArgsConstructor
    public static class RealmAlreadyTakenTag extends Tag {
        public final String realm;
    }
}
