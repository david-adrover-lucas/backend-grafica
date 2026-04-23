package com.drover.demo.backend.repository;

import java.util.Optional;

import com.drover.demo.backend.entity.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CajaRepository extends JpaRepository <Caja, Integer> {
    Optional<Caja> findByCuentaId(Integer cuentaId);
    void deleteByCuentaId(Integer cuentaId);
}
