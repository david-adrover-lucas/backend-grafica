package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.MovimientoStock;

@Repository
public interface Movimiento_stockRepository extends JpaRepository<MovimientoStock, Long> {

    @Query("select m from Movimiento_stock m where m.insumo_id = :insumoId")
    List<MovimientoStock> findByInsumoId(@Param("insumoId") Long insumoId);

    @Query("select m from Movimiento_stock m where m.compra_id = :compraId")
    List<MovimientoStock> findByCompraId(@Param("compraId") Long compraId);

    @Query("select m from Movimiento_stock m where m.venta_id = :ventaId")
    List<MovimientoStock> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select m from Movimiento_stock m where m.detalle_venta_id = :detalleVentaId")
    List<MovimientoStock> findByDetalleVentaId(@Param("detalleVentaId") Long detalleVentaId);

    List<MovimientoStock> findByTipo(String tipo);

    List<MovimientoStock> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
