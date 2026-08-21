package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Detalle_venta;

@Repository
public interface Detalle_ventaRepository extends JpaRepository<Detalle_venta, Long> {
}
