package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {

    List<Reporte> findByTipo(String tipo);

    List<Reporte> findByCreatedAtBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Reporte> findByValorGreaterThanEqual(BigDecimal valor);
}
