package com.scrummanager.business;

import com.scrummanager.business.contract.UserBusinessContract;
import com.scrummanager.domain.dto.request.ChangePasswordRequest;
import com.scrummanager.domain.dto.request.CreateApiKeyRequest;
import com.scrummanager.domain.dto.request.UpdateProfileRequest;
import com.scrummanager.domain.dto.response.ApiKeyResponse;
import com.scrummanager.domain.model.ApiKey;
import com.scrummanager.domain.model.User;
import com.scrummanager.repository.ApiKeyRepository;
import com.scrummanager.repository.UserRepository;
import com.scrummanager.security.PasswordPolicyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBusiness implements UserBusinessContract {

    private static final int RAW_KEY_BYTES = 32;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final UserRepository userRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicyValidator passwordPolicyValidator;

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return findOrThrow(id);
    }

    @Transactional(readOnly = true)
    public List<User> searchByName(String name) {
        return userRepository.searchByNameOrUsername(name);
    }

    @Transactional
    public User updateProfilePhoto(Long id, byte[] photo, Long requesterId) {
        User user = findOrThrow(id);
        assertOwner(user, requesterId, "You can only update your own profile photo");
        user.getProfile().setPhoto(photo);
        return userRepository.save(user);
    }

    @Transactional
    public User updateProfile(Long id, UpdateProfileRequest req, Long requesterId) {
        User user = findOrThrow(id);
        assertOwner(user, requesterId, "You can only update your own profile");
        user.getProfile().setName(req.name());
        user.getProfile().setAvailability(req.availability());
        return userRepository.save(user);
    }

    @Transactional
    public User changePassword(Long id, ChangePasswordRequest req, Long requesterId) {
        User user = findOrThrow(id);
        assertOwner(user, requesterId, "You can only change your own password");
        if (!passwordEncoder.matches(req.currentPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Current password is incorrect");
        }
        passwordPolicyValidator.validate(req.newPassword(), user.getUsername(), user.getEmail(),
                user.getProfile() != null ? user.getProfile().getName() : null);
        user.setPasswordHash(passwordEncoder.encode(req.newPassword()));
        user.setTokenVersion(user.getTokenVersion() + 1);
        LocalDateTime now = LocalDateTime.now();
        user.setLastPasswordChangeAt(now);
        return userRepository.save(user);
    }

    @Transactional
    public ApiKeyResponse createApiKey(Long userId, CreateApiKeyRequest req) {
        User user = findOrThrow(userId);
        String rawKey = generateRawKey();
        String keyHash = sha256Hex(rawKey);
        String keyPrefix = rawKey.substring(0, 8);

        LocalDateTime expiresAt = req.expiresInDays() != null
                ? LocalDateTime.now().plusDays(req.expiresInDays())
                : null;

        ApiKey apiKey = ApiKey.builder()
                .owner(user)
                .name(req.name())
                .keyHash(keyHash)
                .keyPrefix(keyPrefix)
                .active(true)
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .build();

        ApiKey saved = apiKeyRepository.save(apiKey);
        return new ApiKeyResponse(saved.getId(), saved.getName(), saved.getKeyPrefix(),
                rawKey, saved.getCreatedAt(), saved.getExpiresAt());
    }

    @Transactional(readOnly = true)
    public List<ApiKeyResponse> listApiKeys(Long userId) {
        return apiKeyRepository.findByOwnerIdAndActiveTrue(userId).stream()
                .map(k -> new ApiKeyResponse(k.getId(), k.getName(), k.getKeyPrefix(),
                        null, k.getCreatedAt(), k.getExpiresAt()))
                .toList();
    }

    @Transactional
    public void deleteApiKey(Long userId, Long keyId) {
        ApiKey key = apiKeyRepository.findById(keyId)
                .orElseThrow(() -> new IllegalArgumentException("API key not found: " + keyId));
        if (!key.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You can only delete your own API keys");
        }
        key.setActive(false);
        apiKeyRepository.save(key);
    }

    public User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    // ─── helpers ──────────────────────────────────────────────────────────────

    private static void assertOwner(User user, Long requesterId, String message) {
        if (!user.getId().equals(requesterId)) {
            throw new AccessDeniedException(message);
        }
    }

    private static String generateRawKey() {
        byte[] bytes = new byte[RAW_KEY_BYTES];
        SECURE_RANDOM.nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    private static String sha256Hex(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
