package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO CITA SOLICITUD
 * 
 * USO FRONTEND (CLIENTE): Enviar en POST /api/cliente/citas
 * 
 * BODY JSON:
 * {
 *   "mascotaId": 1,
 *   "fecha": "2026-05-20",
 *   "hora": "10:00",
 *   "problema": "Mi perro no come desde ayer"
 * }
 * 
 * VALIDACIONES DEL BACKEND:
 * - fecha no puede ser el día actual (mínimo mañana)
 * - hora debe estar entre 08:00 y 23:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaSolicitud {
    
    @NotNull(message = "Debe seleccionar una mascota")
    private Long mascotaId;
    
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;
    
    @NotBlank(message = "Debe describir el problema")
    private String problema;
}