package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    @Query("select d from Detalle_venta d where d.venta_id = :ventaId")
    List<DetalleVenta> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select d from Detalle_venta d where d.producto_id = :productoId")
    List<DetalleVenta> findByProductoId(@Param("productoId") Long productoId);
}
