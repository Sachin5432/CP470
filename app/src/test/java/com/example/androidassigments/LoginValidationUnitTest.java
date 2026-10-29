package com.example.androidassigments;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests directly testing {@link LoginValidator} app class.
 */
public class LoginValidationUnitTest {

    @Test
    public void testValidEmailAddresses() {
        assertTrue(LoginValidator.isValidEmail("user@domain.com"));
        assertTrue(LoginValidator.isValidEmail("student@university.edu"));
        assertTrue(LoginValidator.isValidEmail("test.user@gmail.com"));
        assertTrue(LoginValidator.isValidEmail("john.doe+tag@company.co.uk"));
    }

    @Test
    public void testInvalidEmailAddresses() {
        assertFalse(LoginValidator.isValidEmail("invalid-email"));
        assertFalse(LoginValidator.isValidEmail("user@"));
        assertFalse(LoginValidator.isValidEmail("@domain.com"));
        assertFalse(LoginValidator.isValidEmail(""));
        assertFalse(LoginValidator.isValidEmail("   "));
        assertFalse(LoginValidator.isValidEmail(null));
        assertFalse(LoginValidator.isValidEmail("user@domain"));
    }

    @Test
    public void testValidPasswords() {
        assertTrue(LoginValidator.isValidPassword("password123"));
        assertTrue(LoginValidator.isValidPassword("a"));
        assertTrue(LoginValidator.isValidPassword("  securePass1  "));
    }

    @Test
    public void testInvalidPasswords() {
        assertFalse(LoginValidator.isValidPassword(""));
        assertFalse(LoginValidator.isValidPassword("   "));
        assertFalse(LoginValidator.isValidPassword(null));
    }
}
