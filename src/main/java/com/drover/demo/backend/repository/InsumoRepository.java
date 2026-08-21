package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Insumo;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
