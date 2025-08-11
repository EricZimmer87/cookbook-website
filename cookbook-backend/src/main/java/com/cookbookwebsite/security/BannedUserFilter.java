package com.cookbookwebsite.security;

import com.cookbookwebsite.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class BannedUserFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public BannedUserFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            // If your principal is a CustomUserDetails with userId, use that directly:
            Object principal = auth.getPrincipal();
            Integer userId = null;
            String email = null;

            if (principal instanceof CustomUserDetails cud) {
                userId = cud.getUserId();
                email = cud.getUsername();
            } else {
                // Fallback: look up by username (email)
                email = auth.getName();
            }

            var userOpt = (userId != null)
                    ? userRepository.findById(userId)
                    : userRepository.findByUserEmail(email);

            if (userOpt.isPresent() && userOpt.get().isBanned()) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.setContentType("application/json");
                res.getWriter().write("""
                  {"code":"ACCOUNT_BANNED","message":"Your account is banned.","timestamp":%d}
                """.formatted(System.currentTimeMillis()));
                return;
            }
        }
        chain.doFilter(req, res);
    }
}