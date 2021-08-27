package com.evatool.common.util;

public class FunctionalErrorCodesUtil {

    private FunctionalErrorCodesUtil() {
    }

    // 400 Not found codes.
    public static final int IMPORT_EXPORT_JSON_INVALID = 1;
    public static final int USERNAME_INVALID = 2;
    public static final int REALM_INVALID = 3;
    public static final int EMAIL_INVALID = 4;
    public static final int PASSWORD_EMPTY_OR_NULL = 5;
    public static final int PASSWORD_NOT_SECURE_ENOUGH = 6;

    // 401 Unauthorized codes.
    public static final int INVALID_CREDENTIALS = 1001;

    // 403 Forbidden codes.
    public static final int CROSS_REALM_ACCESS = 3001;

    // 404 Not found codes.
    public static final int ENTITY_NOT_FOUND = 4001;
    public static final int LOGIN_REALM_NOT_FOUND = 4002;
    public static final int LOGIN_USERNAME_NOT_FOUND = 4003;

    // 409 Conflict codes.
    public static final int VALUE_REFERENCED_BY_IMPACT = 9001;
    public static final int VARIANT_REFERENCED_BY_REQUIREMENT = 9002;
    public static final int STAKEHOLDER_REFERENCED_BY_IMPACT = 9003;
    public static final int IMPACT_REFERENCED_BY_REQUIREMENT_DELTA = 9004;
    public static final int REGISTER_USERNAME_ALREADY_EXISTS = 9005;
    public static final int REGISTER_EMAIL_ALREADY_EXISTS = 9006;
    public static final int REGISTER_REALM_ALREADY_EXISTS = 9007;
    
}
