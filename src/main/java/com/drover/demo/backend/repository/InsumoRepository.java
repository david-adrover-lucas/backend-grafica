package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Insumo;



@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
   

    List<Insumo> findByProveedorId(Long proveedorId);

    List<Insumo> findByActivo(Boolean activo);
    
    List<Insumo> findByNombreContainingIgnoreCase(String nombre);
    
    List<Insumo> findByUnidad(String unidad);
}
