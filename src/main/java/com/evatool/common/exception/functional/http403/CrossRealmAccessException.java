package com.evatool.common.exception.functional.http403;

import com.evatool.common.util.FunctionalErrorCodesUtil;

public class CrossRealmAccessException extends ForbiddenException {
    public CrossRealmAccessException() {
        super("This entity does belong to a different realm", FunctionalErrorCodesUtil.CROSS_REALM_ACCESS, null);
    }
}
