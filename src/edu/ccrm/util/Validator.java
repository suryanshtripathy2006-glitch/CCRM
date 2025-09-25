package edu.ccrm.util;

public class Validator {
    public static void validateEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}