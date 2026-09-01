package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.CostosEscalaTercerizado;

@Repository
public interface CostosEscalaTercerizadoRepository extends JpaRepository<CostosEscalaTercerizado, Long> {

    @Query("select c from Costos_escala_tercerizado c where c.trabajo_tercerizado_id = :trabajoTercerizadoId")
    List<CostosEscalaTercerizado> findByTrabajoTercerizadoId(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId);

    List<CostosEscalaTercerizado> findByActivo(Boolean activo);

    @Query("""
            select c from Costos_escala_tercerizado c
            where c.trabajo_tercerizado_id = :trabajoTercerizadoId
              and c.activo = true
              and c.cantidad_desde <= :cantidad
              and (c.cantidad_hasta is null or c.cantidad_hasta >= :cantidad)
            """)
    List<CostosEscalaTercerizado> findEscalasActivasParaCantidad(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId,
            @Param("cantidad") BigDecimal cantidad);
}
