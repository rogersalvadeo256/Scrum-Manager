package com.scrummanager.security;

import com.scrummanager.config.AppSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PasswordPolicyValidator {

    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("\\d");
    private static final Pattern SPECIAL = Pattern.compile("[^A-Za-z0-9]");

    private final AppSecurityProperties securityProperties;

    public void validate(String password, String... forbiddenFragments) {
        int minLength = securityProperties.getPassword().getMinLength();

        if (!StringUtils.hasText(password) || password.length() < minLength) {
            throw new IllegalArgumentException("Password must contain at least " + minLength + " characters");
        }
        if (!UPPERCASE.matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!LOWERCASE.matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!DIGIT.matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }
        if (!SPECIAL.matcher(password).find()) {
            throw new IllegalArgumentException("Password must contain at least one special character");
        }

        String normalizedPassword = password.toLowerCase(Locale.ROOT);
        Arrays.stream(forbiddenFragments)
                .filter(StringUtils::hasText)
                .map(value -> value.toLowerCase(Locale.ROOT).trim())
                .filter(value -> value.length() >= 3)
                .filter(normalizedPassword::contains)
                .findFirst()
                .ifPresent(fragment -> {
                    throw new IllegalArgumentException("Password cannot contain personal information");
                });
    }
}
