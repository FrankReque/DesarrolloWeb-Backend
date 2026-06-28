package com.example.demo.Controllers;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.CitaSolicitud;
import com.example.demo.DTO.MascotaSolicitud;
import com.example.demo.DTO.RespuestaApi;
import com.example.demo.Service.CitaService;
import com.example.demo.Service.MascotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * CONTROLADOR DEL CLIENTE
 * 
 * USO FRONTEND (ANGULAR) - PANTALLA CLIENTE:
 * 
 * RUTA PROTEGIDA: Solo accesible para usuarios con rol CLIENTE
 * 
 * 1. POST /api/cliente/mascotas → Registrar mascota
 *    - Formulario: nombre, tipo(PERRO/GATO), raza, edad, fotoUrl(opcional)
 *    - Validación: no puede tener 2 mascotas con el mismo nombre
 * 
 * 2. GET /api/cliente/mascotas → Ver "TUS MASCOTAS"
 *    - Lista de todas las mascotas del cliente logueado
 *    - Mostrar como cards con foto, nombre, tipo, raza, edad
 * 
 * 3. POST /api/cliente/citas → Solicitar cita
 *    - Select de mascota, input fecha, input hora, textarea problema
 *    - Validaciones: fecha mínimo mañana, hora entre 8am-11pm
 * 
 * 4. GET /api/cliente/citas → Ver mis citas
 *    - Historial de citas con estados (PENDIENTE, ACEPTADA, RECHAZADA, etc.)
 * 
 * 5. PATCH /api/cliente/citas/{id}/aceptar-reagendacion → Aceptar reagendación
 *    - Cuando un veterinario propone nueva fecha, el cliente la acepta
 * 
 * 6. PATCH /api/cliente/citas/{id}/rechazar-reagendacion → Rechazar reagendación
 *    - El cliente rechaza la nueva fecha propuesta, cita queda RECHAZADA
 */
@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENTE')")  // ← Solo CLIENTE puede acceder
public class ClienteController {
    
    private final MascotaService mascotaServicio;
    private final CitaService citaServicio;
    
    /**
     * REGISTRAR MASCOTA
     * 
     * PARA ANGULAR:
     * - URL: POST http://localhost:8080/api/cliente/mascotas
     * - withCredentials: true
     * - Body: { "nombre": "Firulais", "tipo": "PERRO", "raza": "Labrador", "edad": 3, "fotoUrl": "https://..." }
     * - Formulario en sección "Registrar Mascota"
     * - Select tipo: <option value="PERRO">Perro</option> <option value="GATO">Gato</option>
     * - Si ya existe mascota con ese nombre → error 409
     * - Después de registrar, redirigir a "TUS MASCOTAS"
     */
        @PostMapping("/mascotas")
    public ResponseEntity<RespuestaApi> registrarMascota(
            @Valid @RequestBody MascotaSolicitud solicitud,
            Principal principal) {  // ← Obtiene usuario autenticado
        
        String correo = principal.getName(); // El correo está en getName()
        return ResponseEntity.ok(mascotaServicio.registrarMascota(solicitud, correo));
    }
    
    @GetMapping("/mascotas")
    public ResponseEntity<RespuestaApi> listarMisMascotas(Principal principal) {
        String correo = principal.getName();
        return ResponseEntity.ok(mascotaServicio.listarMisMascotas(correo));
    }
    
    /**
     * LISTAR MIS MASCOTAS ("TUS MASCOTAS")
     * 
     * PARA ANGULAR:
     * - URL: GET http://localhost:8080/api/cliente/mascotas
     * - withCredentials: true
     * - Mostrar en cards o tabla:
     *   <div *ngFor="let mascota of mascotas" class="card">
     *     <img [src]="mascota.fotoUrl || 'assets/default-pet.png'">
     *     <h3>{{mascota.nombre}}</h3>
     *     <p>{{mascota.tipo}} - {{mascota.raza}}</p>
     *     <p>Edad: {{mascota.edad}} años</p>
     *   </div>
     */
    
    /**
     * SOLICITAR CITA
     * 
     * PARA ANGULAR:
     * - URL: POST http://localhost:8080/api/cliente/citas
     * - withCredentials: true
     * - Body: { "mascotaId": 1, "fecha": "2026-05-25", "hora": "10:00", "problema": "No come desde ayer" }
     * - Formulario:
     *   • Select mascota: <option *ngFor="let m of mascotas" [value]="m.id">{{m.nombre}}</option>
     *   • Input fecha: type="date" [min]="manana" (validar no sea hoy)
     *   • Input hora: type="time" min="08:00" max="23:00"
     *   • Textarea problema: rows="4"
     * - Después de solicitar, mostrar mensaje de éxito y redirigir a "Mis Citas"
     */
    @PostMapping("/citas")
    public ResponseEntity<RespuestaApi> solicitarCita(
            @Valid @RequestBody CitaSolicitud solicitud,
            Principal principal) {
        
        String correo = principal.getName();
        return ResponseEntity.ok(citaServicio.solicitarCita(solicitud, correo));
    }
    
    /**
     * VER MIS CITAS
     * 
     * PARA ANGULAR:
     * - URL: GET http://localhost:8080/api/cliente/citas
     * - withCredentials: true
     * - Mostrar historial con estados y colores:
     *   • PENDIENTE → amarillo
     *   • ACEPTADA → verde
     *   • RECHAZADA → rojo
     *   • REAGENDADA → azul (con botones aceptar/rechazar)
     *   • ATENDIDA → gris
     */
    @GetMapping("/citas")
    public ResponseEntity<RespuestaApi> misCitas(Principal principal) {
        String correo = principal.getName();
        return ResponseEntity.ok(citaServicio.misCitas(correo));
    }
    
    /**
     * ACEPTAR REAGENDACIÓN
     * 
     * PARA ANGULAR:
     * - URL: PATCH http://localhost:8080/api/cliente/citas/1/aceptar-reagendacion
     * - withCredentials: true
     * - Botón "Aceptar nueva fecha" cuando estado es REAGENDADA
     * - La cita toma la nueva fecha/hora y pasa a ACEPTADA
     */
    @PatchMapping("/citas/{id}/aceptar-reagendacion")
    public ResponseEntity<RespuestaApi> aceptarReagendacion(@PathVariable Integer id) {
        return ResponseEntity.ok(citaServicio.aceptarReagendacion(id));
    }
    
    /**
     * RECHAZAR REAGENDACIÓN
     * 
     * PARA ANGULAR:
     * - URL: PATCH http://localhost:8080/api/cliente/citas/1/rechazar-reagendacion
     * - withCredentials: true
     * - Botón "Rechazar" cuando estado es REAGENDADA
     * - La cita pasa a RECHAZADA definitivamente
     * - No hay opción de volver a reagendar desde el cliente
     */
    @PatchMapping("/citas/{id}/rechazar-reagendacion")
    public ResponseEntity<RespuestaApi> rechazarReagendacion(@PathVariable Integer id) {
        return ResponseEntity.ok(citaServicio.rechazarReagendacion(id));
    }
}