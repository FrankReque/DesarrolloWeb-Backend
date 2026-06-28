package com.example.demo.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO REAGENDAR SOLICITUD
 * 
 * USO FRONTEND (VETERINARIO): Enviar en PATCH /api/veterinario/citas/{id}/reagendar
 * 
 * BODY JSON:
 * {
 *   "nuevaFecha": "2026-05-25",
 *   "nuevaHora": "14:00"
 * }
 * 
 * RESULTADO: La cita pasa a estado REAGENDADA.
 * El cliente recibe notificación y debe aceptar o rechazar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReagendarSolicitud {
    
    @NotNull
    private LocalDate nuevaFecha;
    
    @NotNull
    private LocalTime nuevaHora;
}