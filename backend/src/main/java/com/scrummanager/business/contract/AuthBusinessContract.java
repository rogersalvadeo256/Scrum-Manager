package com.scrummanager.business.contract;

import com.scrummanager.domain.dto.request.ActivateAccountRequest;
import com.scrummanager.domain.dto.request.LoginRequest;
import com.scrummanager.domain.dto.request.RegisterRequest;
import com.scrummanager.domain.model.User;

import java.time.Instant;

public interface AuthBusinessContract {
    User register(RegisterRequest req);
    LoginResult login(LoginRequest req);
    User activateAccount(ActivateAccountRequest req);
    void blacklistToken(String tokenId, Instant expiresAt);
}
