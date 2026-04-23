package com.drover.demo.backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.ProductoInsumo;


public interface ProductoInsumoRepository extends JpaRepository<ProductoInsumo, Integer> {
    List<ProductoInsumo> findByProductoId(Integer productoId);
    List<ProductoInsumo> findByInsumoId(Integer insumoId);
}
