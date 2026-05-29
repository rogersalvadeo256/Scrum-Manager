package com.scrummanager.facade.contract;

import com.scrummanager.domain.dto.request.ActivateAccountRequest;
import com.scrummanager.domain.dto.request.LoginRequest;
import com.scrummanager.domain.dto.request.RegisterRequest;
import com.scrummanager.domain.dto.response.AuthResponse;
import com.scrummanager.domain.dto.response.UserResponse;

public interface AuthFacadeContract {
    UserResponse register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
    void activateAccount(ActivateAccountRequest req);
    void logout(String authorizationHeader);
}
