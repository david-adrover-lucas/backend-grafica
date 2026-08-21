package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto_trabajo_tercerizado;

@Repository
public interface Producto_trabajo_tercerizadoRepository extends JpaRepository<Producto_trabajo_tercerizado, Long> {
}
