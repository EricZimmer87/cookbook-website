package com.cookbookwebsite.security;

import com.cookbookwebsite.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

// Wraps User object to be usable with Spring Security.
public class CustomUserDetails implements UserDetails {
    // This object is what gets stored in the SecurityContext.
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return user.getUserId();
    }

    public String getUserEmail() {
        return user.getUserEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Handle roles/permissions here
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().toUpperCase()));
    }

    @Override public String getPassword() { return user.getPasswordHash(); }
    @Override public String getUsername() { return user.getUserEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
