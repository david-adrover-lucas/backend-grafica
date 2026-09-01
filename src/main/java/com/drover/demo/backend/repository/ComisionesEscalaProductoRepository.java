package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.ComisionesEscalaProducto;



@Repository
public interface ComisionesEscalaProductoRepository extends JpaRepository<ComisionesEscalaProducto, Long> {
    @Query("SELECT c.montoComision FROM ComisionesEscalaProducto c " +
           "WHERE c.producto.id = :productoId " +
           "AND :cantidad >= c.cantidadDesde " +
           "AND (c.cantidadHasta IS NULL OR :cantidad <= c.cantidadHasta) " +
           "AND c.activo = true")
    Optional<BigDecimal> encontrarComisionPorRango(Long productoId, BigDecimal cantidad);
    List<ComisionesEscalaProducto> findByProductoId(Long productoId);
}
