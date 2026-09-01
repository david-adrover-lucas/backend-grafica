package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.ProductoTrabajoTercerizado;

@Repository
public interface ProductoTrabajoTercerizadoRepository extends JpaRepository<ProductoTrabajoTercerizado, Long> {

    @Query("select p from Producto_trabajo_tercerizado p where p.producto_id = :productoId")
    List<ProductoTrabajoTercerizado> findByProductoId(@Param("productoId") Long productoId);

    @Query("select p from Producto_trabajo_tercerizado p where p.trabajo_tercerizado_id = :trabajoTercerizadoId")
    List<ProductoTrabajoTercerizado> findByTrabajoTercerizadoId(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId);
}
