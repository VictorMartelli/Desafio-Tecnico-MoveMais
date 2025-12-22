package com.movemais.estoque.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de Segurança Arquitetural.
 * Preparada para autenticação Stateless (JWT) e Controle de Acesso por Camadas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desabilita CSRF: Essencial para APIs REST que não mantêm estado em cookies
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Política de Sessão: Configurada como STATELESS (preparação para JWT/OAuth2)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 3. Regras de Autorização e Filtragem por Camadas
            .authorizeHttpRequests(auth -> auth
                // Permite acesso ao Console do H2 (Desenvolvimento)
                .requestMatchers("/h2-console/**").permitAll()

                // Exemplo de Filtragem por Verbos e Camadas (RBAC):
                // No futuro, bastaria trocar .permitAll() por .hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/produtos/**").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/produtos/**").permitAll()
                
                // Garante que qualquer outro endpoint não mapeado exija autenticação
                .anyRequest().authenticated()
            )
            
            // 4. Configuração para Frames: Permite que o Console do H2 funcione em Iframes
            .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
}