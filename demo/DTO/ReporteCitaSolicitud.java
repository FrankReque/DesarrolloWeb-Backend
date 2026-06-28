package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO REPORTE CITA
 * 
 * USO FRONTEND (VETERINARIO): Enviar en PATCH /api/veterinario/citas/{id}/reporte
 * 
 * BODY JSON:
 * {
 *   "reporte": "El perro presenta infección leve. Se recetó antibióticos."
 * }
 * 
 * El veterinario escribe esto después de atender la cita.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCitaSolicitud {
    
    @NotBlank(message = "El reporte no puede estar vacío")
    private String reporte;
}