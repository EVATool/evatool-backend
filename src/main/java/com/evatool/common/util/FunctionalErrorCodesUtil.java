package com.evatool.common.util;

public class FunctionalErrorCodesUtil {

    private FunctionalErrorCodesUtil() {
    }

    // 409 Conflict codes.
    public static final int VALUE_REFERENCED_BY_IMPACT = 1001;
    public static final int VARIANT_REFERENCED_BY_REQUIREMENT = 1002;
    public static final int STAKEHOLDER_REFERENCED_BY_IMPACT = 1003;
    public static final int IMPACT_REFERENCED_BY_REQUIREMENT_DELTA = 1004;

    // 404 Not found codes.
    public static final int LOGIN_REALM_NOT_FOUND = 2001;
    public static final int LOGIN_USERNAME_NOT_FOUND = 2002;



}
