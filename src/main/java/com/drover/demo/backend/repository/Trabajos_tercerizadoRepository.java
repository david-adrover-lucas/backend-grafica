package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Trabajos_tercerizado;

@Repository
public interface Trabajos_tercerizadoRepository extends JpaRepository<Trabajos_tercerizado, Long> {
}
