package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.drover.demo.backend.entity.Presupuesto;


@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    Optional<Presupuesto> findByNroPresupuesto(String nroPresupuesto);

    List<Presupuesto> findByEstado(String estado);

    List<Presupuesto> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Presupuesto> findByClienteId(Long clienteId);

    List<Presupuesto> findByRevendedorId(Long revendedorId);

    boolean existsByNroPresupuesto(String nroPresupuesto);
}
