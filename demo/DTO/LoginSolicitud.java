package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO LOGIN SOLICITUD
 * 
 * USO FRONTEND: Enviar en POST /api/auth/login
 * 
 * BODY JSON:
 * {
 *   "correo": "cliente@email.com",
 *   "contrasena": "123456"
 * }
 * 
 * RESPUESTA EXITOSA:
 * {
 *   "exito": true,
 *   "mensaje": "Login exitoso",
 *   "datos": {
 *     "rol": "CLIENTE",
 *     "nombres": "Juan",
 *     "correo": "cliente@email.com"
 *   }
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSolicitud {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
}