package com.drover.demo.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Vendedor;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    List<Vendedor> findByActivo(Boolean activo);

    List<Vendedor> findByFechaAltaBetween(LocalDate desde, LocalDate hasta);
}
