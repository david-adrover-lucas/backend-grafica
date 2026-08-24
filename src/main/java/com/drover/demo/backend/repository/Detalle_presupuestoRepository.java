package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Detalle_presupuesto;

@Repository
public interface Detalle_presupuestoRepository extends JpaRepository<Detalle_presupuesto, Long> {

    @Query("select d from Detalle_presupuesto d where d.presupuesto_id = :presupuestoId")
    List<Detalle_presupuesto> findByPresupuestoId(@Param("presupuestoId") Long presupuestoId);

    @Query("select d from Detalle_presupuesto d where d.producto_id = :productoId")
    List<Detalle_presupuesto> findByProductoId(@Param("productoId") Long productoId);
}
