package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.ProductoTrabajoTercerizado;


@Repository
public interface ProductoTrabajoTercerizadoRepository extends JpaRepository<ProductoTrabajoTercerizado, Long> {

    List<ProductoTrabajoTercerizado> findByProductoId(Long productoId);
    

    List<ProductoTrabajoTercerizado> findByTrabajoTercerizadoId(Long trabajoTercerizadoId);
}

