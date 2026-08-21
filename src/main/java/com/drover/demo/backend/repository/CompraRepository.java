package com.drover.demo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
}
