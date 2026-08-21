package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto_insumo;

@Repository
public interface Producto_insumoRepository extends JpaRepository<Producto_insumo, Long> {
}
