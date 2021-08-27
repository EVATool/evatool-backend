package com.evatool.common.util;

public class FunctionalErrorCodesUtil {

    private FunctionalErrorCodesUtil() {
    }

    // 409 Conflict codes.
    public static final int VALUE_REFERENCED_BY_IMPACT = 1001;
    public static final int VARIANT_REFERENCED_BY_REQUIREMENT = 1002;
    public static final int STAKEHOLDER_REFERENCED_BY_IMPACT = 1003;
    public static final int IMPACT_REFERENCED_BY_REQUIREMENT_DELTA = 1004;
    public static final int REGISTER_USERNAME_ALREADY_EXISTS = 1051;
    public static final int REGISTER_EMAIL_ALREADY_EXISTS = 1052;

    // 404 Not found codes.
    public static final int LOGIN_REALM_NOT_FOUND = 2001;
    public static final int LOGIN_USERNAME_NOT_FOUND = 2002;



}
