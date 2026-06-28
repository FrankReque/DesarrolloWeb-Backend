package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * ENTIDAD TOKEN DE VERIFICACIÓN
 * 
 * USO FRONTEND (MODAL DE VERIFICACIÓN):
 * 1. Cliente se registra → Backend envía email con código de 6 dígitos
 * 2. Frontend muestra modal solicitando el token
 * 3. Cliente ingresa token → POST /api/auth/verificar-token
 * 4. Si coincide y no expiró → registro exitoso
 * 5. Si no coincide o expiró → error, puede solicitar nuevo token
 */
@Entity
@Table(name = "token_verificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
    
    @Column(nullable = false, length = 10)
    private String token;
    
    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;
    
    @Column(nullable = false)
    private Boolean usado = false;
    
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
