package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.MovimientoStock;



@Repository
public interface MovimientoStockRepository extends JpaRepository<MovimientoStock, Long> {

    // 🌟 CORRECCIÓN: Agregado "OrderByFechaDesc" al final del nombre
    List<MovimientoStock> findByInsumoIdOrderByFechaDesc(Long insumoId);

    List<MovimientoStock> findByTipo(String tipo);

    // 🌟 CORRECCIÓN: Agregado "OrderByFechaDesc" al final del nombre
    List<MovimientoStock> findByFechaBetweenOrderByFechaDesc(LocalDateTime desde, LocalDateTime hasta);
}


