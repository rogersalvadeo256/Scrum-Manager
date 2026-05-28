package com.scrummanager.service;

import com.scrummanager.config.AppSecurityProperties;
import com.scrummanager.domain.entity.ProjectMember;
import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.enums.RequestStatus;
import com.scrummanager.repository.ProjectMemberRepository;
import com.scrummanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundMaintenanceService {

    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final CacheInvalidationService cacheInvalidationService;
    private final DomainEventPublisher domainEventPublisher;
    private final TokenStateService tokenStateService;
    private final AppSecurityProperties securityProperties;

    @Value("${app.background.token-cleanup-ms}")
    private long tokenCleanupMs;

    @Scheduled(fixedDelayString = "${app.background.token-cleanup-ms}")
    public void purgeExpiredTokenState() {
        tokenStateService.purgeExpiredTokens();
        log.debug("Expired local token state purged on {} ms cadence", tokenCleanupMs);
    }

    @Scheduled(cron = "${app.background.unlock-cron}")
    @Transactional
    public void unlockExpiredAccounts() {
        List<User> users = userRepository.findUsersReadyToUnlock(LocalDateTime.now());
        for (User user : users) {
            user.setAccountLockedUntil(null);
            user.setFailedLoginAttempts(0);
            cacheInvalidationService.evictUserCaches(user);
            domainEventPublisher.publish(
                    "security.account.unlocked",
                    user.getId(),
                    "user",
                    user.getId(),
                    Map.of("username", user.getUsername()));
        }
    }

    @Scheduled(cron = "${app.background.password-reminder-cron}")
    @Transactional(readOnly = true)
    public void publishPasswordExpiryReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderWindow = now.plusDays(securityProperties.getPassword().getReminderWindowDays());
        for (User user : userRepository.findUsersWithPasswordExpiryBetween(now, reminderWindow)) {
            domainEventPublisher.publish(
                    "security.password.expiring",
                    user.getId(),
                    "user",
                    user.getId(),
                    Map.of(
                            "username", user.getUsername(),
                            "passwordExpiresAt", user.getPasswordExpiresAt()));
        }
    }

    @Scheduled(cron = "${app.background.invite-reminder-cron}")
    @Transactional(readOnly = true)
    public void publishPendingInviteReminders() {
        LocalDate cutoff = LocalDate.now().minusDays(securityProperties.getBackground().getInviteReminderAfterDays());
        for (ProjectMember member : projectMemberRepository.findPendingInvitesSentBefore(RequestStatus.ON_HOLD, cutoff)) {
            domainEventPublisher.publish(
                    "project.invite.reminder",
                    member.getInvitedById(),
                    "project-member",
                    member.getId(),
                    Map.of(
                            "projectId", member.getProjectId(),
                            "targetUserId", member.getUserId(),
                            "inviteSentDate", member.getInviteSentDate()));
        }
    }
}
