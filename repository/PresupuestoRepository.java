package com.drover.demo.backend.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.Presupuesto;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Integer> {
    List<Presupuesto> findByCodigo(String codigo);
    List<Presupuesto> findByClienteId(Integer clienteId);
    List<Presupuesto> findByTotalGreaterThan(BigDecimal total);        
}
