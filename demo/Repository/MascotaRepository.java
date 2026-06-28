package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Mascota;

/**
 * REPOSITORIO MASCOTA
 * 
 * USO FRONTEND:
 * - GET /api/cliente/mascotas → findByClienteId() (muestra "TUS MASCOTAS")
 * - Valida que no haya 2 mascotas con mismo nombre por cliente: existsByClienteIdAndNombre()
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    
    // Obtiene todas las mascotas de un cliente (para "TUS MASCOTAS")
    List<Mascota> findByClienteId(Integer clienteId);
    
    // Valida que no exista mascota con mismo nombre para el mismo cliente
    boolean existsByClienteIdAndNombre(Integer clienteId, String nombre);
}