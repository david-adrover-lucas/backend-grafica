package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.ProductoInsumo;



@Repository
public interface ProductoInsumoRepository extends JpaRepository<ProductoInsumo, Long> {

    List<ProductoInsumo> findByProductoId(Long productoId);
}
