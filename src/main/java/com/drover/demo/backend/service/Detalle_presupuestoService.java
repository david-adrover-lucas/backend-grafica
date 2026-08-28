package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Detalle_presupuesto;
import com.drover.demo.backend.repository.Detalle_presupuestoRepository;

@Service
public class Detalle_presupuestoService {

    private final Detalle_presupuestoRepository detallePresupuestoRepository;

    public Detalle_presupuestoService(Detalle_presupuestoRepository detallePresupuestoRepository) {
        this.detallePresupuestoRepository = detallePresupuestoRepository;
    }

    public List<Detalle_presupuesto> listar() {}
    public Optional<Detalle_presupuesto> buscarPorId(Long id) {}
    public Detalle_presupuesto guardar(Detalle_presupuesto detallePresupuesto) { }
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularCosto(){}
    private BigDecimal calcularPrecioUnitario(){}
    
}
