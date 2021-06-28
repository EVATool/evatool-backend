package com.evatool.common.util;

public class AuthUtil {

    private AuthUtil() {
    }

    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";

    public static final String ByAdmin = "hasRole('" + ADMIN_ROLE + "')";
    public static final String ByAdminOrUser = "hasRole('" + AuthUtil.ADMIN_ROLE + "') || hasRole('" + AuthUtil.USER_ROLE + "')";

}
