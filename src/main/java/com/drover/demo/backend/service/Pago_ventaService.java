package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.PagoVenta;
import com.drover.demo.backend.repository.PagoVentaRepository;

@Service
public class Pago_ventaService {

    private final PagoVentaRepository pagoVentaRepository;

    public Pago_ventaService(PagoVentaRepository pagoVentaRepository) {
        this.pagoVentaRepository = pagoVentaRepository;
    }

    public List<PagoVenta> listar() {}
    public Optional<PagoVenta> buscarPorId(Long id) {}
    public PagoVenta guardar(PagoVenta pagoVenta) {}
    public void eliminarPorId(Long id) { }

    
}
