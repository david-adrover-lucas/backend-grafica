package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivo(Boolean activo);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Permite agrupar o listar todos los productos que se venden por "m2", "lineal" o "unidad"
    List<Producto> findByUnidadVenta(String unidadVenta);
}