package com.evatool.common.util;

import java.util.regex.Pattern;

public class UUIDUtil {

    private UUIDUtil() {
    }

    private static final Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String uuidStr) {
        if (uuidStr == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(uuidStr).matches();
    }
}
