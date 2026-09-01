package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByProveedorId(Long proveedorId);

 
    Optional<Compra> findByNumeroCompra(String numeroCompra);

  
    List<Compra> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}

