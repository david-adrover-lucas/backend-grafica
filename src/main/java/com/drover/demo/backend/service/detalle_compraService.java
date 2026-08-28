package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Detalle_compra;
import com.drover.demo.backend.repository.Detalle_compraRepository;

@Service
public class Detalle_compraService {

    private final Detalle_compraRepository detalleCompraRepository;

    public Detalle_compraService(Detalle_compraRepository detalleCompraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
    }

    public List<Detalle_compra> listar() {}
    public Optional<Detalle_compra> buscarPorId(Long id) {}
    public Detalle_compra guardar(Detalle_compra  detalleCompra) {}
    public void eliminarPorId(Long id) {}

    private BigDecimal validarPrecio(){}
    private BigDecimal validarCantidad(){}
    private BigDecimal calcularSubtotal(BigDecimal cantida, BigDecimal precioUnitario){}
}
