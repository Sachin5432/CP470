package com.example.androidassigments;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoginValidationUnitTest {

    // Standard email regex pattern matching android.util.Patterns.EMAIL_ADDRESS format
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    @Test
    public void testValidEmailAddresses() {
        assertTrue(isValidEmail("user@domain.com"));
        assertTrue(isValidEmail("student@university.edu"));
        assertTrue(isValidEmail("test.user@gmail.com"));
    }

    @Test
    public void testInvalidEmailAddresses() {
        assertFalse(isValidEmail("invalid-email"));
        assertFalse(isValidEmail("user@"));
        assertFalse(isValidEmail("@domain.com"));
        assertFalse(isValidEmail(""));
    }

    @Test
    public void testPasswordNotEmpty() {
        assertTrue(isValidPassword("password123"));
        assertFalse(isValidPassword(""));
        assertFalse(isValidPassword("   ".trim()));
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty() && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    private boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty();
    }
}
