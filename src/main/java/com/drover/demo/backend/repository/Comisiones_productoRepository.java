package com.drover.demo.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Comisiones_producto;


@Repository
public interface Comisiones_productoRepository extends JpaRepository<Comisiones_producto, Long> {

    @Query("select c from Comisiones_producto c where c.producto_id = :productoId")
    List<Comisiones_producto> findByProductoId(@Param("productoId") Long productoId);

    List<Comisiones_producto> findByActivo(Boolean activo);
    @Query("select c from Comisiones_producto c where c.producto_id = :productoId and c.activo = :activo")
    Optional<Comisiones_producto> findByProductoIdAndActivo(@Param("productoId") Long productoId,
            @Param("activo") Boolean activo);
}
