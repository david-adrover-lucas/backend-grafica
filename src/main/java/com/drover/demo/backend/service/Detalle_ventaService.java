package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.DetalleVenta;
import com.drover.demo.backend.repository.DetalleVentaRepository;

@Service
public class Detalle_ventaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public Detalle_ventaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    public List<DetalleVenta> listar() {}
    public Optional<DetalleVenta> buscarPorId(Long id) {}
    public DetalleVenta guardar(DetalleVenta detalleVenta) {}
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularPrecioUnitario(){}
    private BigDecimal calcularSubtotal(){}
    
}
