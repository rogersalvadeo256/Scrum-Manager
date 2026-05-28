package com.scrummanager.controller;

import com.scrummanager.dto.request.ActivateAccountRequest;
import com.scrummanager.dto.request.LoginRequest;
import com.scrummanager.dto.request.RegisterRequest;
import com.scrummanager.dto.response.AuthResponse;
import com.scrummanager.dto.response.UserResponse;
import com.scrummanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activate(@Valid @RequestBody ActivateAccountRequest req) {
        authService.activateAccount(req);
        return ResponseEntity.noContent().build();
    }
}
