package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.detalle_compra;

@Repository
public interface detalle_compraRepository extends JpaRepository<detalle_compra, Long> {

    @Query("select d from detalle_compra d where d.compra_id = :compraId")
    List<detalle_compra> findByCompraId(@Param("compraId") Long compraId);

    @Query("select d from detalle_compra d where d.insumo_id = :insumoId")
    List<detalle_compra> findByInsumoId(@Param("insumoId") Long insumoId);
}
