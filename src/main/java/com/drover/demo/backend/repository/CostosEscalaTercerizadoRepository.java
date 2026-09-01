package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.CostosEscalaTercerizado;


import java.util.Optional;

@Repository
public interface CostosEscalaTercerizadoRepository extends JpaRepository<CostosEscalaTercerizado, Long> {


    List<CostosEscalaTercerizado> findByTrabajoTercerizadoId(Long trabajoTercerizadoId);

    List<CostosEscalaTercerizado> findByActivo(Boolean activo);

    /**
     * CONSULTA CONTABLE CRÍTICA (Para los Bloques 5, 7 y 8 del PDF):
     * Recibe el ID del servicio tercerizado y la cantidad (m2, lineales o tazas) requerida,
     * busca en qué rango de volumen encaja y devuelve el precio unitario exacto que te cobra el proveedor.
     */
    @Query("SELECT c.precioUnitario FROM CostosEscalaTercerizado c " +
           "WHERE c.trabajoTercerizado.id = :trabajoTercerizadoId " +
           "AND :cantidad >= c.cantidadDesde " +
           "AND (c.cantidadHasta IS NULL OR :cantidad <= c.cantidadHasta) " +
           "AND c.activo = true")
    Optional<BigDecimal> encontrarCostoTercerizadoPorRango(Long trabajoTercerizadoId, BigDecimal cantidad);
}

