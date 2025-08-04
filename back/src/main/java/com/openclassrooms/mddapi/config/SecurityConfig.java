package com.openclassrooms.mddapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("=== SECURITY CONFIG LOADED ===");
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/api/auth/**").permitAll()
                        // .requestMatchers(HttpMethod.GET, "/api/topics").permitAll()
                        // .requestMatchers(HttpMethod.GET, "/api/topics/*").permitAll()
                        .anyRequest().permitAll());
        return http.build();
    }
}


