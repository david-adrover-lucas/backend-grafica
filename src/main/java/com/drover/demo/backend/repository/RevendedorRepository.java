package com.drover.demo.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Revendedor;

@Repository
public interface RevendedorRepository extends JpaRepository<Revendedor, Long> {

    List<Revendedor> findByActivo(Boolean activo);

    List<Revendedor> findByFechaAltaBetween(LocalDate desde, LocalDate hasta);
}
