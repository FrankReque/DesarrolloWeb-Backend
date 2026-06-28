package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO VERIFICAR TOKEN
 * 
 * USO FRONTEND: Enviar en POST /api/auth/verificar-token
 * 
 * BODY JSON (desde el modal de verificación):
 * {
 *   "correo": "nuevo@email.com",
 *   "token": "123456"
 * }
 * 
 * SI ES CORRECTO: Registro completo, usuario creado.
 * SI ES INCORRECTO: Error, puede solicitar reenvío.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificarTokenSolicitud {
    
    @NotBlank
    @Email
    private String correo;
    
    @NotBlank(message = "El token es obligatorio")
    private String token;
}
