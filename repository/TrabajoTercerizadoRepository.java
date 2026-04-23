package com.drover.demo.backend.repository;

import com.drover.demo.backend.entity.TrabajoTercerizado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabajoTercerizadoRepository extends JpaRepository<TrabajoTercerizado, Integer> {

    List<TrabajoTercerizado> findByEstado(String estado);

    List<TrabajoTercerizado> findByEmpresaId(Integer empresaId);

    List<TrabajoTercerizado> findByEmpresaIdAndEstado(Integer empresaId, String estado);

    List<TrabajoTercerizado> findByEstadoOrderByFechaRetiroDesc(String estado);
}