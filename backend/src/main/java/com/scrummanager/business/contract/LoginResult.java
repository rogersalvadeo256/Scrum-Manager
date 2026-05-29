package com.scrummanager.business.contract;

import com.scrummanager.domain.model.User;
import com.scrummanager.security.AuthenticatedUserPrincipal;

public record LoginResult(User user, AuthenticatedUserPrincipal principal) {
}
