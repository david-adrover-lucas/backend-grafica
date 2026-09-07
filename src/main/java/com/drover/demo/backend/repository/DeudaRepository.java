package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Deuda;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Long> {

    List<Deuda> findByEstado(String estado);

    List<Deuda> findByTipo(String tipo);

    List<Deuda> findByPersonaId(Long personaId);

    List<Deuda> findByProveedorId(Long proveedorId);

    List<Deuda> findByFechaGeneracionBetween(LocalDateTime desde, LocalDateTime hasta);

    boolean existsByPersonaIdAndTipoAndObservaciones(Long personaId, String tipo, String observaciones);

    boolean existsByTrabajoTercerizadoVentaIdAndTipo(Long trabajoTercerizadoVentaId, String tipo);

    boolean existsByVentaIdAndPersonaIdAndTipo(Long ventaId, Long personaId, String tipo);
}
