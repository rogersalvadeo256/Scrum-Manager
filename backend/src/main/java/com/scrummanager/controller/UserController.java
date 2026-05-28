package com.scrummanager.controller;

import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.security.UserDetailsServiceImpl;
import com.scrummanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> search(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchByName(name));
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<UserResponse> updatePhoto(@PathVariable Long id,
                                                    @RequestBody byte[] photo,
                                                    @AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(userService.updateProfilePhoto(id, photo));
    }
}
