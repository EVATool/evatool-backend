package com.evatool.common.util;

public class AuthUtil {

    private AuthUtil() {
    }

    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";

    public static final String BY_ADMIN = "hasRole('" + ADMIN_ROLE + "')";
    public static final String BY_ADMIN_OR_USER = "hasRole('" + AuthUtil.ADMIN_ROLE + "') || hasRole('" + AuthUtil.USER_ROLE + "')";

}
