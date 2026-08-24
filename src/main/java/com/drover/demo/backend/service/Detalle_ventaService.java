package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Detalle_venta;
import com.drover.demo.backend.repository.Detalle_ventaRepository;

@Service
public class Detalle_ventaService {

    private final Detalle_ventaRepository detalleVentaRepository;

    public Detalle_ventaService(Detalle_ventaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    public List<Detalle_venta> listar() {
        return detalleVentaRepository.findAll();
    }

    public Optional<Detalle_venta> buscarPorId(Long id) {
        return detalleVentaRepository.findById(id);
    }

    public Detalle_venta guardar(Detalle_venta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public void eliminarPorId(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}
