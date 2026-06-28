package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO REGISTRO CLIENTE SOLICITUD
 *
 * USO FRONTEND: Enviar en POST /api/auth/registro
 *
 * BODY JSON: { "correo": "nuevo@email.com", "contrasena": "123456", "nombres":
 * "Juan", "apellidos": "Pérez", "telefono": "987654321", "dni": "12345678" }
 *
 * FLUJO: 1. Frontend envía estos datos 2. Backend genera token y envía email 3.
 * Frontend muestra modal pidiendo token 4. Frontend envía token en POST
 * /api/auth/verificar-token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroClienteSolicitud {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no es válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    private String telefono;

    private String dni;
}
