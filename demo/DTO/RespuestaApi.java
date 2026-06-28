package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO RESPUESTA API
 * 
 * USO FRONTEND: Todas las respuestas del backend usan este formato estándar.
 * 
 * ESTRUCTURA JSON DE RESPUESTA:
 * {
 *   "exito": true/false,
 *   "mensaje": "mensaje descriptivo",
 *   "datos": { ... } // cualquier objeto o lista
 * }
 * 
 * EJEMPLO EN ANGULAR:
 * this.http.post('/api/auth/login', credenciales).subscribe(
 *   (respuesta: RespuestaApi) => {
 *     if (respuesta.exito) {
 *       // redirigir según respuesta.datos.rol
 *     } else {
 *       // mostrar respuesta.mensaje en toast/alerta
 *     }
 *   }
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaApi {
    
    private Boolean exito;
    private String mensaje;
    private Object datos;
    
    public static RespuestaApi exito(String mensaje, Object datos) {
        return RespuestaApi.builder()
                .exito(true)
                .mensaje(mensaje)
                .datos(datos)
                .build();
    }
    
    public static RespuestaApi error(String mensaje) {
        return RespuestaApi.builder()
                .exito(false)
                .mensaje(mensaje)
                .datos(null)
                .build();
    }
}
