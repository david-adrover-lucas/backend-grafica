package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Pago_venta;
import com.drover.demo.backend.repository.Pago_ventaRepository;

@Service
public class Pago_ventaService {

    private final Pago_ventaRepository pagoVentaRepository;

    public Pago_ventaService(Pago_ventaRepository pagoVentaRepository) {
        this.pagoVentaRepository = pagoVentaRepository;
    }

    public List<Pago_venta> listar() {}
    public Optional<Pago_venta> buscarPorId(Long id) {}
    public Pago_venta guardar(Pago_venta pagoVenta) {}
    public void eliminarPorId(Long id) { }

    
}
