package com.drover.demo.backend.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.PresupuestoDetalle;

public interface PresupuestoDetalleRepository extends JpaRepository<PresupuestoDetalle, Integer>{
    List<PresupuestoDetalle> findByPresupuestoId(Integer presupuestoId);
    List<PresupuestoDetalle> findByProductoId(Integer productoId);
    List<PresupuestoDetalle> findByCantidad(Integer cantidad);
    List<PresupuestoDetalle> findByAlto(BigDecimal alto);
    List<PresupuestoDetalle> findByAncho(BigDecimal ancho);
    List<PresupuestoDetalle> findByCosto(BigDecimal costo);
}
