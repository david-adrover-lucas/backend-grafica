package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Detalle_presupuesto;

@Repository
public interface Detalle_presupuestoRepository extends JpaRepository<Detalle_presupuesto, Long> {
}
