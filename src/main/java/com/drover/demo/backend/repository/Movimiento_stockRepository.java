package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Movimiento_stock;

@Repository
public interface Movimiento_stockRepository extends JpaRepository<Movimiento_stock, Long> {

    @Query("select m from Movimiento_stock m where m.insumo_id = :insumoId")
    List<Movimiento_stock> findByInsumoId(@Param("insumoId") Long insumoId);

    @Query("select m from Movimiento_stock m where m.compra_id = :compraId")
    List<Movimiento_stock> findByCompraId(@Param("compraId") Long compraId);

    @Query("select m from Movimiento_stock m where m.venta_id = :ventaId")
    List<Movimiento_stock> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select m from Movimiento_stock m where m.detalle_venta_id = :detalleVentaId")
    List<Movimiento_stock> findByDetalleVentaId(@Param("detalleVentaId") Long detalleVentaId);

    List<Movimiento_stock> findByTipo(String tipo);

    List<Movimiento_stock> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
