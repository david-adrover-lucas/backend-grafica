package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.PagoDeuda;

@Repository
public interface PagoDeudaRepository extends JpaRepository<PagoDeuda, Long> {

    List<PagoDeuda> findByDeudaId(Long deudaId);

    List<PagoDeuda> findByCuentaId(Long cuentaId);

    List<PagoDeuda> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
