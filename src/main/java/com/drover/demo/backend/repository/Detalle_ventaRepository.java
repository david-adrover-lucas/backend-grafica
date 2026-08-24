package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Detalle_venta;

@Repository
public interface Detalle_ventaRepository extends JpaRepository<Detalle_venta, Long> {

    @Query("select d from Detalle_venta d where d.venta_id = :ventaId")
    List<Detalle_venta> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select d from Detalle_venta d where d.producto_id = :productoId")
    List<Detalle_venta> findByProductoId(@Param("productoId") Long productoId);
}
