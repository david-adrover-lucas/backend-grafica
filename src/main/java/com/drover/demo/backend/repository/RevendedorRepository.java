package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Revendedor;

@Repository
public interface RevendedorRepository extends JpaRepository<Revendedor, Long> {
}
