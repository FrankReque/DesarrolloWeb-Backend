package com.example.demo.Repository;

import com.example.demo.Model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * REPOSITORIO VENTA (Futuro)
 * 
 * USO FRONTEND (FUTURO): Historial de compras del cliente.
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    List<Venta> findByClienteId(Long clienteId);
}