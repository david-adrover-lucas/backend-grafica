package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto_insumo;

@Repository
public interface Producto_insumoRepository extends JpaRepository<Producto_insumo, Long> {

    @Query("select p from Producto_insumo p where p.producto_id = :productoId")
    List<Producto_insumo> findByProductoId(@Param("productoId") Long productoId);

    @Query("select p from Producto_insumo p where p.insumo_id = :insumoId")
    List<Producto_insumo> findByInsumoId(@Param("insumoId") Long insumoId);
}
