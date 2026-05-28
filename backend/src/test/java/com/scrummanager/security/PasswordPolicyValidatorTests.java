package com.scrummanager.security;

import com.scrummanager.config.AppSecurityProperties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordPolicyValidatorTests {

    private final PasswordPolicyValidator validator = new PasswordPolicyValidator(new AppSecurityProperties());

    @Test
    void acceptsStrongPasswords() {
        assertDoesNotThrow(() -> validator.validate("UltraSecure#2026", "roger", "roger@example.com"));
    }

    @Test
    void rejectsPasswordsWithoutRequiredComplexity() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("alllowercase123!", "roger"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("NoDigitsOrSpecial", "roger"));
    }

    @Test
    void rejectsPasswordsContainingPersonalInformation() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("RogerSecure#2026", "roger"));
    }
}
