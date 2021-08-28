package com.evatool.common.util;

public class FunctionalErrorCodesUtil {

    private FunctionalErrorCodesUtil() {
        // TODO Update failed cuz not found (for all entities)
        // TODO Deletion failed cuz not found (for all entities)
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
    public static final int LOGIN_REALM_NOT_FOUND = 4001;
    public static final int LOGIN_USERNAME_NOT_FOUND = 4002;

    public static final int ANALYSIS_NOT_FOUND = 4101;
    public static final int STAKEHOLDER_NOT_FOUND = 4102;
    public static final int VALUE_NOT_FOUND = 4103;
    public static final int IMPACT_NOT_FOUND = 4104;
    public static final int VARIANT_NOT_FOUND = 4105;
    public static final int REQUIREMENT_NOT_FOUND = 4106;
    public static final int REQUIREMENT_DELTA_NOT_FOUND = 4107;

    public static final int ANALYSIS_UPDATE_FAILED_NOT_FOUND = 4201;
    public static final int STAKEHOLDER_UPDATE_FAILED_NOT_FOUND = 4202;
    public static final int VALUE_UPDATE_FAILED_NOT_FOUND = 4203;
    public static final int IMPACT_UPDATE_FAILED_NOT_FOUND = 4204;
    public static final int VARIANT_UPDATE_FAILED_NOT_FOUND = 4205;
    public static final int REQUIREMENT_UPDATE_FAILED_NOT_FOUND = 4206;
    public static final int REQUIREMENT_DELTA_UPDATE_FAILED_NOT_FOUND = 4207;

    public static final int ANALYSIS_DELETION_FAILED_NOT_FOUND = 4301;
    public static final int STAKEHOLDER_DELETION_FAILED_NOT_FOUND = 4302;
    public static final int VALUE_DELETION_FAILED_NOT_FOUND = 4303;
    public static final int IMPACT_DELETION_FAILED_NOT_FOUND = 4304;
    public static final int VARIANT_DELETION_FAILED_NOT_FOUND = 4305;
    public static final int REQUIREMENT_DELETION_FAILED_NOT_FOUND = 4306;
    public static final int REQUIREMENT_DELTA_DELETION_FAILED_NOT_FOUND = 4307;

    // 409 Conflict codes.
    public static final int VALUE_REFERENCED_BY_IMPACT = 9001;
    public static final int VARIANT_REFERENCED_BY_REQUIREMENT = 9002;
    public static final int STAKEHOLDER_REFERENCED_BY_IMPACT = 9003;
    public static final int IMPACT_REFERENCED_BY_REQUIREMENT_DELTA = 9004;
    public static final int REGISTER_USERNAME_ALREADY_EXISTS = 9005;
    public static final int REGISTER_EMAIL_ALREADY_EXISTS = 9006;
    public static final int REGISTER_REALM_ALREADY_EXISTS = 9007;

}
