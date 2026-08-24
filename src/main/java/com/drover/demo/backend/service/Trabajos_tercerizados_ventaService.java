package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Trabajos_tercerizados_venta;
import com.drover.demo.backend.repository.Trabajos_tercerizados_ventaRepository;

@Service
public class Trabajos_tercerizados_ventaService {

    private final Trabajos_tercerizados_ventaRepository trabajosTercerizadosVentaRepository;

    public Trabajos_tercerizados_ventaService(
            Trabajos_tercerizados_ventaRepository trabajosTercerizadosVentaRepository) {
        this.trabajosTercerizadosVentaRepository = trabajosTercerizadosVentaRepository;
    }

    public List<Trabajos_tercerizados_venta> listar() {
        return trabajosTercerizadosVentaRepository.findAll();
    }

    public Optional<Trabajos_tercerizados_venta> buscarPorId(Long id) {
        return trabajosTercerizadosVentaRepository.findById(id);
    }

    public Trabajos_tercerizados_venta guardar(Trabajos_tercerizados_venta trabajosTercerizadosVenta) {
        return trabajosTercerizadosVentaRepository.save(trabajosTercerizadosVenta);
    }

    public void eliminarPorId(Long id) {
        trabajosTercerizadosVentaRepository.deleteById(id);
    }
}
