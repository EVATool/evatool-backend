package com.evatool.common.validation;

import java.util.regex.Pattern;

public class PasswordValidation {

    private PasswordValidation() {
    }

    private static final Pattern PASSWORD_REGEX_PATTERN =
            Pattern.compile("^[A-Za-z0-9_.-]+$");

    public static String validatePassword(String password) {
        if (password == null) {
            return "The password cannot be null";
        } else if (password.equals("")) {
            return "The password cannot be empty";
        }
        return null;
    }

    public static String validatePasswordSecurity(String password) {
        // TODO add not secure enough?
        return null;
    }
}
