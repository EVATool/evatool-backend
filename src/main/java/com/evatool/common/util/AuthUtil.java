package com.evatool.common.util;

public class AuthUtil {

    private AuthUtil() {
    }

    public static final String READER_ROLE = "reader";
    public static final String WRITER_ROLE = "writer";

    public static final String BY_READER = "hasRole('" + READER_ROLE + "')";
    public static final String BY_WRITER = "hasRole('" + WRITER_ROLE + "')";

}
