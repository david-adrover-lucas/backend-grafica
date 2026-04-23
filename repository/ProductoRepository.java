package com.drover.demo.backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByTipo(Producto.TipoProducto tipo);
    List<Producto> findByActivoTrue();
    Optional<Producto> findById(Integer productoId);
    
}
