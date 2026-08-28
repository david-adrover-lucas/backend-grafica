package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Detalle_compra;

@Repository
public interface Detalle_compraRepository extends JpaRepository<Detalle_compra, Long> {

    @Query("select d from detalle_compra d where d.compra_id = :compraId")
    List<Detalle_compra> findByCompraId(@Param("compraId") Long compraId);

    @Query("select d from detalle_compra d where d.insumo_id = :insumoId")
    List<Detalle_compra> findByInsumoId(@Param("insumoId") Long insumoId);
}
