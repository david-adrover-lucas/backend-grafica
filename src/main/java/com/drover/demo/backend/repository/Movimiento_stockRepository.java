package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Movimiento_stock;

@Repository
public interface Movimiento_stockRepository extends JpaRepository<Movimiento_stock, Long> {
}
