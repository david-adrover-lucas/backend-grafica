package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.DetallePresupuesto;
import com.drover.demo.backend.repository.DetallePresupuestoRepository;

@Service
public class Detalle_presupuestoService {

    private final DetallePresupuestoRepository detallePresupuestoRepository;

    public Detalle_presupuestoService(DetallePresupuestoRepository detallePresupuestoRepository) {
        this.detallePresupuestoRepository = detallePresupuestoRepository;
    }

    public List<DetallePresupuesto> listar() {}
    public Optional<DetallePresupuesto> buscarPorId(Long id) {}
    public DetallePresupuesto guardar(DetallePresupuesto detallePresupuesto) { }
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularCosto(){}
    private BigDecimal calcularPrecioUnitario(){}
    
}
