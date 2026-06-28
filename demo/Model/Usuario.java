package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ENTIDAD USUARIO
 * 
 * USO FRONTEND: 
 * - Login: Se envía email + password. El backend busca aquí.
 * - Registro: Se crea un usuario con rol 'CLIENTE' (después de verificar email).
 * - Los veterinarios también tienen usuario (creado por admin).
 * 
 * CAMBIO IMPORTANTE: El rol ahora es String (VARCHAR) en lugar de Enum.
 * La validación está en la base de datos con CHECK.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellidos;

    @Column(length = 20)
    private String telefono;

    @Column(length = 20, unique = true)
    private String dni;

    @Column(length = 100)
    private String especialidad;        // Solo para veterinarios

    @Column(precision = 10, scale = 2)
    private java.math.BigDecimal sueldo; // Solo para veterinarios

    @Column(length = 20)
    private String estadoVet;           // 'ACTIVO' o 'INACTIVO' - Solo para veterinarios

    @Column(length = 255)
    private String recoveryToken;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false, length = 20)
    private String rol;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();
}
