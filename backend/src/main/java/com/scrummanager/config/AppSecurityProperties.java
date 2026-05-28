package com.scrummanager.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app.security")
public class AppSecurityProperties {

    @Valid
    private final Password password = new Password();

    @Valid
    private final Lockout lockout = new Lockout();

    @Valid
    private final Background background = new Background();

    @Getter
    @Setter
    public static class Password {
        @Min(12)
        private int minLength = 12;

        @Min(1)
        private int maxAgeDays = 90;

        @Min(1)
        private int reminderWindowDays = 7;
    }

    @Getter
    @Setter
    public static class Lockout {
        @Min(1)
        private int maxAttempts = 5;

        @Min(1)
        private int durationMinutes = 15;
    }

    @Getter
    @Setter
    public static class Background {
        @Min(1)
        private int inviteReminderAfterDays = 3;
    }
}
