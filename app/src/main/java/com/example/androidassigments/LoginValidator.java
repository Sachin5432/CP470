package com.example.androidassigments;

import java.util.regex.Pattern;

/**
 * Pure Java utility for validating login credentials.
 * Decoupled from Android framework for fast and reliable unit testing.
 */
public class LoginValidator {

    // Standard RFC-compliant email regex pattern for JVM unit testing
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    private LoginValidator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Checks if the given email is non-null and matches valid email format.
     *
     * @param email The email string to validate
     * @return true if email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        return !trimmed.isEmpty() && EMAIL_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Checks if the given password is non-null and non-empty after trimming.
     *
     * @param password The password string to validate
     * @return true if password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return !password.trim().isEmpty();
    }
}
