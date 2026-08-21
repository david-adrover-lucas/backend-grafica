package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Costos_escala_tercerizado;

@Repository
public interface Costos_escala_tercerizadoRepository extends JpaRepository<Costos_escala_tercerizado, Long> {
}
