package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Token;

/**
 * REPOSITORIO TOKEN VERIFICACIÓN
 * 
 * USO FRONTEND: El backend usa esto internamente para validar el token
 * que el cliente ingresa en el modal de verificación.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    
    // Busca el token más reciente no usado para un correo
    Optional<Token> findTopByCorreoAndUsadoFalseOrderByCreadoEnDesc(String correo);
}
