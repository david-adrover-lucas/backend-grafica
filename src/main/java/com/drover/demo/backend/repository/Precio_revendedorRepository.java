package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Precio_revendedor;

@Repository
public interface Precio_revendedorRepository extends JpaRepository<Precio_revendedor, Long> {
}
