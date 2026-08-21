package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Trabajos_tercerizados_venta;

@Repository
public interface Trabajos_tercerizados_ventaRepository extends JpaRepository<Trabajos_tercerizados_venta, Long> {
}
