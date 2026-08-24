package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Costos_escala_tercerizado;

@Repository
public interface Costos_escala_tercerizadoRepository extends JpaRepository<Costos_escala_tercerizado, Long> {

    @Query("select c from Costos_escala_tercerizado c where c.trabajo_tercerizado_id = :trabajoTercerizadoId")
    List<Costos_escala_tercerizado> findByTrabajoTercerizadoId(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId);

    List<Costos_escala_tercerizado> findByActivo(Boolean activo);

    @Query("""
            select c from Costos_escala_tercerizado c
            where c.trabajo_tercerizado_id = :trabajoTercerizadoId
              and c.activo = true
              and c.cantidad_desde <= :cantidad
              and (c.cantidad_hasta is null or c.cantidad_hasta >= :cantidad)
            """)
    List<Costos_escala_tercerizado> findEscalasActivasParaCantidad(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId,
            @Param("cantidad") BigDecimal cantidad);
}
