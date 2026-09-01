package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.util.Optional;


import com.drover.demo.backend.entity.PreciosEscalaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface PreciosEscalaProductoRepository extends JpaRepository<PreciosEscalaProducto, Long> {

    // Spring Data JPA entra automáticamente al objeto producto y filtra por su id
    List<PreciosEscalaProducto> findByProductoId(Long productoId);

    // Mantenemos nuestra query inteligente para resolver los precios en las ventas
    @Query("SELECT p.porcentajeGananciaEscala FROM PreciosEscalaProducto p " +
           "WHERE p.producto.id = :productoId " +
           "AND :cantidad >= p.cantidadDesde " +
           "AND (p.cantidadHasta IS NULL OR :cantidad <= p.cantidadHasta)")
    Optional<BigDecimal> encontrarPorcentajePorEscala(Long productoId, BigDecimal cantidad);
}


