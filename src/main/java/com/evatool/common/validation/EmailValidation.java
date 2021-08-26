package com.evatool.common.validation;

import java.util.regex.Pattern;

public class EmailValidation {

    private EmailValidation() {
    }

    private static final Pattern EMAIL_REGEX_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static String validateEmail(String email) {
        if (email == null || email.equals("")) {
            return "Email cannot be empty";
        } else if (!EMAIL_REGEX_PATTERN.matcher(email).matches()) {
            return "Email \"" + email + "\" is invalid";
        }
        return null;
    }
}
