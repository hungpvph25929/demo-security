package com.example.server.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Autowired
    private SecurityTokenProvider securityTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityTokenFilter securityTokenFilter = new SecurityTokenFilter(securityTokenProvider);
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(securityTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/", "/api/authentication/**").permitAll()
                .requestMatchers("/api/**", "/api/authentication/logout")
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
