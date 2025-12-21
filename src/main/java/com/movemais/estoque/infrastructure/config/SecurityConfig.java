package com.movemais.estoque.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para permitir o POST do Insomnia
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas as requisições em ambiente de dev
            )
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Necessário para acessar o console do H2
        
        return http.build();
    }
}