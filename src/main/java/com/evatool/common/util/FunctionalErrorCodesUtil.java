package com.evatool.common.util;

public class FunctionalErrorCodesUtil {

    private FunctionalErrorCodesUtil() {
    }

    // 400 Not found codes.
    public static final int IMPORT_EXPORT_JSON_INVALID = 400_001;
    public static final int USERNAME_INVALID = 400_002;
    public static final int REALM_INVALID = 400_003;
    public static final int EMAIL_INVALID = 400_004;
    public static final int PASSWORD_EMPTY_OR_NULL = 400_005;
    public static final int PASSWORD_NOT_SECURE_ENOUGH = 400_006;

    // 401 Unauthorized codes.
    public static final int INVALID_CREDENTIALS = 401_001;

    // 403 Forbidden codes.
    public static final int CROSS_REALM_ACCESS = 403_001;
    public static final int REMOTE_IP_BLOCKED = 403_002;

    // 404 Not found codes.
    public static final int LOGIN_REALM_NOT_FOUND = 404_001;
    public static final int LOGIN_USERNAME_NOT_FOUND = 404_002;

    public static final int ANALYSIS_FIND_FAILED_NOT_FOUND = 404_101;
    public static final int STAKEHOLDER_FIND_FAILED_NOT_FOUND = 404_102;
    public static final int VALUE_FIND_FAILED_NOT_FOUND = 404103;
    public static final int IMPACT_FIND_FAILED_NOT_FOUND = 404_104;
    public static final int VARIANT_FIND_FAILED_NOT_FOUND = 404_105;
    public static final int REQUIREMENT_FIND_FAILED_NOT_FOUND = 404_106;
    public static final int REQUIREMENT_DELTA_FIND_FAILED_NOT_FOUND = 404_107;

    public static final int ANALYSIS_UPDATE_FAILED_NOT_FOUND = 404_201;
    public static final int STAKEHOLDER_UPDATE_FAILED_NOT_FOUND = 404_202;
    public static final int VALUE_UPDATE_FAILED_NOT_FOUND = 404_203;
    public static final int IMPACT_UPDATE_FAILED_NOT_FOUND = 404_204;
    public static final int VARIANT_UPDATE_FAILED_NOT_FOUND = 404_205;
    public static final int REQUIREMENT_UPDATE_FAILED_NOT_FOUND = 404_206;
    public static final int REQUIREMENT_DELTA_UPDATE_FAILED_NOT_FOUND = 404_207;

    public static final int ANALYSIS_DELETION_FAILED_NOT_FOUND = 404_301;
    public static final int STAKEHOLDER_DELETION_FAILED_NOT_FOUND = 404_302;
    public static final int VALUE_DELETION_FAILED_NOT_FOUND = 404_303;
    public static final int IMPACT_DELETION_FAILED_NOT_FOUND = 404_304;
    public static final int VARIANT_DELETION_FAILED_NOT_FOUND = 404_305;
    public static final int REQUIREMENT_DELETION_FAILED_NOT_FOUND = 404_306;
    public static final int REQUIREMENT_DELTA_DELETION_FAILED_NOT_FOUND = 404_307;

    // 409 Conflict codes.
    public static final int VALUE_REFERENCED_BY_IMPACT = 409_001;
    public static final int VARIANT_REFERENCED_BY_REQUIREMENT = 409_002;
    public static final int STAKEHOLDER_REFERENCED_BY_IMPACT = 409_003;
    public static final int IMPACT_REFERENCED_BY_REQUIREMENT_DELTA = 409_004;
    public static final int REGISTER_USERNAME_ALREADY_EXISTS = 409_005;
    public static final int REGISTER_EMAIL_ALREADY_EXISTS = 409_006;
    public static final int REGISTER_REALM_ALREADY_EXISTS = 409_007;

}
