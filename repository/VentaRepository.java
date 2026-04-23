package com.drover.demo.backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.drover.demo.backend.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer>   {
    List<Venta> findByClienteId(Integer clienteId);
    List<Venta> findByUsuarioId(Integer usuarioId);
    List<Venta> findByEstado(Venta.EstadoVenta estado);
        
}
