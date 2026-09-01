package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.TrabajosTercerizado;

@Repository
public interface TrabajosTercerizadoRepository extends JpaRepository<TrabajosTercerizado, Long> {
    
    List<TrabajosTercerizado> findByProveedorId(Long proveedorId);

    List<TrabajosTercerizado> findByActivo(Boolean activo);

    List<TrabajosTercerizado> findByNombreContainingIgnoreCase(String nombre);
}

