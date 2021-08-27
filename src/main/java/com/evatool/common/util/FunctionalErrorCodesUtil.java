package com.evatool.common.util;

public class FunctionalErrorCodesUtil {

    private FunctionalErrorCodesUtil() {
    }

    // 400 Not found codes.
    public static final int ENTITY_NOT_FOUND = 1;
    public static final int USERNAME_NOT_FOUND = 2;
    public static final int REALM_NOT_FOUND = 3;

    // 401 Unauthorized codes.
    public static final int INVALID_CREDENTIALS = 1001;

    // 403 Forbidden codes.
    public static final int CROSS_REALM_ACCESS = 3001;

    // 404 Not found codes.
    public static final int LOGIN_REALM_NOT_FOUND = 4001;
    public static final int LOGIN_USERNAME_NOT_FOUND = 4002;

    // 409 Conflict codes.
    public static final int VALUE_REFERENCED_BY_IMPACT = 9001;
    public static final int VARIANT_REFERENCED_BY_REQUIREMENT = 9002;
    public static final int STAKEHOLDER_REFERENCED_BY_IMPACT = 9003;
    public static final int IMPACT_REFERENCED_BY_REQUIREMENT_DELTA = 9004;
    public static final int REGISTER_USERNAME_ALREADY_EXISTS = 9051;
    public static final int REGISTER_EMAIL_ALREADY_EXISTS = 9052;
    public static final int REGISTER_REALM_ALREADY_EXISTS = 9053;

    // 422 Unprocessable entity codes.
    public static final int PROPERTY_CANNOT_BE_NULL = 22001;
    public static final int PROPERTY_CANNOT_BE_UPDATED = 22002;
    public static final int PROPERTY_CANNOT_IS_INVALID = 22003;
    public static final int PROPERTY_CANNOT_MUST_BE_NULL = 22004;

}
