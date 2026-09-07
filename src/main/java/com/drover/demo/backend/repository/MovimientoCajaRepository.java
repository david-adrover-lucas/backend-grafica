package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.MovimientoCaja;

@Repository
public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {

    List<MovimientoCaja> findByCuentaIdOrderByFechaDesc(Long cuentaId);

    List<MovimientoCaja> findByFechaBetweenOrderByFechaDesc(LocalDateTime desde, LocalDateTime hasta);

    List<MovimientoCaja> findByCuentaIdAndFechaBetweenOrderByFechaDesc(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);

    List<MovimientoCaja> findByTipoAndFechaBetween(String tipo, LocalDateTime desde, LocalDateTime hasta);
}
