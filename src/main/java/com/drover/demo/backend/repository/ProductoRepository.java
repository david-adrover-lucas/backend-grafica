package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
