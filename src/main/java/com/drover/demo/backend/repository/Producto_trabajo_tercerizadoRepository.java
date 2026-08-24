package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto_trabajo_tercerizado;

@Repository
public interface Producto_trabajo_tercerizadoRepository extends JpaRepository<Producto_trabajo_tercerizado, Long> {

    @Query("select p from Producto_trabajo_tercerizado p where p.producto_id = :productoId")
    List<Producto_trabajo_tercerizado> findByProductoId(@Param("productoId") Long productoId);

    @Query("select p from Producto_trabajo_tercerizado p where p.trabajo_tercerizado_id = :trabajoTercerizadoId")
    List<Producto_trabajo_tercerizado> findByTrabajoTercerizadoId(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId);
}
