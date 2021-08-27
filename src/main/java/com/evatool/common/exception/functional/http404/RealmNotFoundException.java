package com.evatool.common.exception.functional.http404;

import com.evatool.common.exception.functional.Tag;
import com.evatool.common.util.FunctionalErrorCodesUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RealmNotFoundException extends NotFoundException {
    public RealmNotFoundException(String realm) {
        super("Realm \"" + realm + "\" not found", FunctionalErrorCodesUtil.LOGIN_REALM_NOT_FOUND, new RealmNotFoundTag(realm));
    }

    @Getter
    @RequiredArgsConstructor
    public static class RealmNotFoundTag extends Tag {
        public final String realm;
    }
}
