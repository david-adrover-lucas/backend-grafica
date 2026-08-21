package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Comisiones_producto;

@Repository
public interface Comisiones_productoRepository extends JpaRepository<Comisiones_producto, Long> {
}
