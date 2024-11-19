package com.example.inventory.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if ("test".equals(System.getProperty("spring.profiles.active"))) {
            http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll(); // Permitir todas las solicitudes
        } else {
            http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();
        }
        return http.build();
    }
}
