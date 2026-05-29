package com.scrummanager.api;

import com.scrummanager.domain.dto.request.ChangePasswordRequest;
import com.scrummanager.domain.dto.request.CreateApiKeyRequest;
import com.scrummanager.domain.dto.request.UpdateProfileRequest;
import com.scrummanager.domain.dto.response.ApiKeyResponse;
import com.scrummanager.domain.dto.response.UserSettingsResponse;
import com.scrummanager.facade.contract.UserFacadeContract;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserSettingsApi {

    private final UserFacadeContract userFacade;

    @GetMapping
    public ResponseEntity<UserSettingsResponse> getMySettings(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(userFacade.getMySettings(principal.getId()));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserSettingsResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest req,
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(userFacade.updateProfile(principal.getId(), req, principal.getId()));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest req,
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        userFacade.changePassword(principal.getId(), req, principal.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api-keys")
    public ResponseEntity<List<ApiKeyResponse>> listApiKeys(
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(userFacade.listApiKeys(principal.getId()));
    }

    @PostMapping("/api-keys")
    public ResponseEntity<ApiKeyResponse> createApiKey(
            @Valid @RequestBody CreateApiKeyRequest req,
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userFacade.createApiKey(principal.getId(), req));
    }

    @DeleteMapping("/api-keys/{keyId}")
    public ResponseEntity<Void> deleteApiKey(
            @PathVariable Long keyId,
            @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        userFacade.deleteApiKey(principal.getId(), keyId);
        return ResponseEntity.noContent().build();
    }
}
