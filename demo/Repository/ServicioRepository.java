package com.example.demo.Repository;

import com.example.demo.Model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * REPOSITORIO SERVICIO (Futuro)
 * 
 * USO FRONTEND (FUTURO): Listar servicios disponibles para ventas.
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    
    List<Servicio> findByActivoTrue();
}