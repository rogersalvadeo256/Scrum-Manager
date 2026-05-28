package com.scrummanager.api;

import com.scrummanager.domain.dto.response.UserResponse;
import com.scrummanager.facade.UserFacade;
import com.scrummanager.security.AuthenticatedUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserFacade userFacade;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> search(@RequestParam String name) {
        return ResponseEntity.ok(userFacade.searchByName(name));
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<UserResponse> updatePhoto(@PathVariable Long id,
                                                    @RequestBody byte[] photo,
                                                    @AuthenticationPrincipal AuthenticatedUserPrincipal principal) {
        return ResponseEntity.ok(userFacade.updateProfilePhoto(id, photo, principal.getId()));
    }
}
