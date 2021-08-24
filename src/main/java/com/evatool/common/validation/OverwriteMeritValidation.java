package com.evatool.common.validation;

public class OverwriteMeritValidation {

    private OverwriteMeritValidation() {
    }

    public static String validateOverwriteMerit(Float overwriteMerit, Float originalMerit) {
        var delta = overwriteMerit - originalMerit;
        if (originalMerit < 0 && delta < 0) {
            return "An impact with a negative value can only be increased";
        } else if (originalMerit < 0 && overwriteMerit > 0) {
            return "An impact with a negative value can only be brought up to 0, not above";
        } else if (originalMerit > 0 && delta > 0) {
            return "An impact with a positive value can only be brought up to its original merit, not above";
        } else if (originalMerit > 0 && overwriteMerit < 0) {
            return "An impact with a positive value can only be brought down to 0, not below";
        }
        return null;
    }
}
