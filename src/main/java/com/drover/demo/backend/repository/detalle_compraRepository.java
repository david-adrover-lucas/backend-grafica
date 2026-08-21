package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.detalle_compra;

@Repository
public interface detalle_compraRepository extends JpaRepository<detalle_compra, Long> {
}
