package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) 
public class SecurityConfig {
    
    /**
     * Bean de PasswordEncoder para encriptar contraseñas.
     * Se usa en VeterinarioService y UsuarioService.
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configuración de seguridad HTTP.
     * Permite CORS y desactiva CSRF para APIs REST.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // ← Sesiones activas
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()      // Login/Register público
                .requestMatchers("/api/public/**").permitAll()    // Endpoints públicos
                .anyRequest().authenticated()                      // ← TODO LO DEMÁS REQUIERE AUTH
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable());  // Usamos login custom, no el de Spring
        
        return http.build();
    }
}