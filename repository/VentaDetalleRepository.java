package com.drover.demo.backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.VentaDetalle;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalle, Integer> {
    List<VentaDetalle> findByVentaId(Integer ventaId);
    List<VentaDetalle> findByProductoId(Integer productoId);
    Optional<VentaDetalle> findById(Integer id);
    void deleteById(Integer id);

    
    
}
