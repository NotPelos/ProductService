package com.example.inventory.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactiva CSRF
                .authorizeHttpRequests()
                .anyRequest().permitAll(); // Permite el acceso a todos los endpoints sin autenticación
        return http.build();
    }
}
