package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.DetallePresupuesto;

@Repository
public interface DetallePresupuestoRepository extends JpaRepository<DetallePresupuesto, Long> {

    @Query("select d from Detalle_presupuesto d where d.presupuesto_id = :presupuestoId")
    List<DetallePresupuesto> findByPresupuestoId(@Param("presupuestoId") Long presupuestoId);

    @Query("select d from Detalle_presupuesto d where d.producto_id = :productoId")
    List<DetallePresupuesto> findByProductoId(@Param("productoId") Long productoId);
}
