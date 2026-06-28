package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * ENTIDAD SERVICIO (Para ventas futuras)
 * 
 * USO FRONTEND (FUTURO):
 * - GET /api/servicios → Listar servicios disponibles (baño, corte, vacunas, etc.)
 * - Los servicios se usarán para crear ventas
 */
@Entity
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(nullable = false)
    private Boolean activo = true;
}
