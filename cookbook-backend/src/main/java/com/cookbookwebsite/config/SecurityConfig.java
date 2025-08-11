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
    // This bean allows Spring to hash and verify passwords with BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Disables CSRF protection. CSRF isn’t needed in stateless APIs (like ones using JWTs).
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            UserRepository userRepository) throws Exception {
        var bannedUserFilter = new BannedUserFilter(userRepository);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Recipes
                        .requestMatchers("/api/recipes").permitAll()
                        .requestMatchers("/api/recipes/*").permitAll()

                        // Reviews
                        .requestMatchers(HttpMethod.GET, "/api/reviews/recipe/**").permitAll()

                        // Mail
                        .requestMatchers("/api/password-reset/**").permitAll()
                        .requestMatchers("/api/test-mail/**").permitAll()

                        // Everything else default (adjust as needed)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(bannedUserFilter, JwtAuthFilter.class);

        return http.build();
    }
}
