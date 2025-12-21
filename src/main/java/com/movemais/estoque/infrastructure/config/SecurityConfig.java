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
            // 1. Desabilita o CSRF para permitir que o Insomnia envie POST/PUT
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Configura as regras de autorização
            .authorizeHttpRequests(auth -> auth
                // Libera todos os endpoints que começam com /api/
                .requestMatchers("/api/**").permitAll() 
                // Libera o console do banco de dados H2
                .requestMatchers("/h2-console/**").permitAll()
                // Qualquer outra requisição deve ser permitida (ambiente de dev)
                .anyRequest().permitAll()
            )
            
            // 3. Necessário para o console do H2 abrir corretamente no navegador
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
}