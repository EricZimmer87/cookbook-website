package com.cookbookwebsite.config;

import com.cookbookwebsite.repository.UserRepository;
import com.cookbookwebsite.security.BannedUserFilter;
import com.cookbookwebsite.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            UserRepository userRepository) throws Exception {

        var bannedUserFilter = new BannedUserFilter(userRepository);

        http
                // Only match /api/** — static assets and SPA routes bypass security entirely
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // uses dev-only CorsConfigurationSource
                .authorizeHttpRequests(auth -> auth
                        // public API endpoints
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/api/password-reset/**", "/api/test-mail/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/recipe/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/recipes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ingredients/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        // everything else under /api/** requires auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(bannedUserFilter, JwtAuthFilter.class);

        return http.build();
    }
}

