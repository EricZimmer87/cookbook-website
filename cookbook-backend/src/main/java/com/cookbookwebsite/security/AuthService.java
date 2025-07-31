package com.cookbookwebsite.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new AccessDeniedException("User not authenticated");
        }
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }


    // Get the current user's email from the SecurityContext
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getName(); // This should be the email if set as subject
    }

    // Check if current user matches target email
    public boolean isCurrentUser(String targetEmail) {
        String currentUserEmail = getCurrentUserEmail();
        return currentUserEmail != null && currentUserEmail.equals(targetEmail);
    }

    // Check if current user has admin role (optional if you use roles)
    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
                authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
    }
}
