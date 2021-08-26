package com.evatool.common.validation;

import java.util.regex.Pattern;

public class UsernameRealmValidation {

    private UsernameRealmValidation() {
    }

    private static final Pattern USERNAME_REALM_REGEX_PATTERN =
            Pattern.compile("^[A-Za-z0-9_.-]+$");

    public static String validateUsernameOrRealm(String value) {
        if (value == null || !USERNAME_REALM_REGEX_PATTERN.matcher(value).matches()) {
            return "The value \"" + value + "\" is not allowed";
        }
        return null;
    }
}
