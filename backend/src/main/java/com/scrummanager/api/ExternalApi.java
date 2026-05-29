package com.scrummanager.api;

import com.scrummanager.domain.dto.response.UserSettingsResponse;
import com.scrummanager.facade.contract.ProjectFacadeContract;
import com.scrummanager.facade.contract.UserFacadeContract;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

/**
 * Publicly reachable endpoints intended for external integrations.
 * All protected routes require an {@code X-Api-Key} header obtained from
 * {@code POST /api/users/me/api-keys}.
 */
@RestController
@RequestMapping("/external")
@RequiredArgsConstructor
@Tag(name = "External API", description = "Endpoints for external integrations. Protected routes require X-Api-Key header.")
public class ExternalApi {

    private final UserFacadeContract userFacade;
    private final ProjectFacadeContract projectFacade;

    /** Public health-check – no authentication required. */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Returns service liveness. No authentication required.")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", Instant.now().toString()));
    }

    /**
     * Returns the public profile of a user.
     * Requires a valid API key in the {@code X-Api-Key} header.
     */
    @GetMapping("/users/{id}/profile")
    @Operation(summary = "Get public user profile",
               description = "Returns publicly visible information about a user.",
               security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<UserSettingsResponse> publicUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.getMySettings(id));
    }

    /**
     * Returns project metrics for the authenticated API-key owner.
     * Requires a valid API key in the {@code X-Api-Key} header.
     */
    @GetMapping("/projects/{projectId}/metrics")
    @Operation(summary = "Get project metrics",
               description = "Returns aggregated metrics for the given project. The API-key owner must be a project member or owner.",
               security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity<?> projectMetrics(@PathVariable Long projectId) {
        Long callerId = resolveCallerId();
        return ResponseEntity.ok(projectFacade.getProjectMetrics(projectId, callerId));
    }

    // ─── helpers ──────────────────────────────────────────────────────────────

    private Long resolveCallerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new org.springframework.security.access.AccessDeniedException("Not authenticated");
        }
        return (Long) auth.getPrincipal();
    }
}
