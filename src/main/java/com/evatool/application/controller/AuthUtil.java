package com.evatool.application.controller;

public class AuthUtil {

    private AuthUtil() {
    }

    public static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";

    public static final String[] READ_ROLES = new String[]{USER_ROLE, ADMIN_ROLE};
    public static final String[] READ_WRITE_ROLES = new String[]{ADMIN_ROLE};

}
