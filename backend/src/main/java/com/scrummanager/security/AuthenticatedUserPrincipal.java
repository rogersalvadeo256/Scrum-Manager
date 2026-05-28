package com.scrummanager.security;

import com.scrummanager.domain.entity.User;
import com.scrummanager.domain.enums.AccountStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class AuthenticatedUserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Integer tokenVersion;
    private final boolean accountNonLocked;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public static AuthenticatedUserPrincipal fromUser(User user) {
        boolean locked = user.getAccountLockedUntil() != null && user.getAccountLockedUntil().isAfter(LocalDateTime.now());
        return new AuthenticatedUserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getTokenVersion() == null ? 0 : user.getTokenVersion(),
                !locked,
                user.getStatus() == AccountStatus.ACTIVE,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
