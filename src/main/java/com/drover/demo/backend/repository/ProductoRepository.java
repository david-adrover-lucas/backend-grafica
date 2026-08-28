package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivo(Boolean activo);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByUnidad_venta(String unidad_venta);
}
