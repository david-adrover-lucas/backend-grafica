package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.HistorialPresupuesto;


public interface HistorialPresupuestoRepository extends JpaRepository<HistorialPresupuesto, Integer> {
    List<HistorialPresupuesto> findByPresupuestoId(Integer presupuestoId);
    List<HistorialPresupuesto> findByFecha(LocalDateTime fecha);
    List<HistorialPresupuesto> findByCreatedAtBetween(LocalDateTime desde, LocalDateTime hasta);
    Integer countByEstado(String estado);
    List<HistorialPresupuesto> findByClienteId(Integer clienteId);
    List<HistorialPresupuesto> findByEstado(String estado);
}
