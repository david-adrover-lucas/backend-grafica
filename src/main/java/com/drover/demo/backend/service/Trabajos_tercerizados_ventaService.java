package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.TrabajosTercerizadosVenta;
import com.drover.demo.backend.repository.TrabajosTercerizadosVentaRepository;

@Service
public class Trabajos_tercerizados_ventaService {

    private final TrabajosTercerizadosVentaRepository trabajosTercerizadosVentaRepository;

    public Trabajos_tercerizados_ventaService(
            TrabajosTercerizadosVentaRepository trabajosTercerizadosVentaRepository) {
        this.trabajosTercerizadosVentaRepository = trabajosTercerizadosVentaRepository;
    }

    public List<TrabajosTercerizadosVenta> listar() {}
    public List<TrabajosTercerizadosVenta>listarTipo(){}
    public List<TrabajosTercerizadosVenta> listarFechaEnvio(){}
    public Optional<TrabajosTercerizadosVenta> buscarPorId(Long id) {}
    public TrabajosTercerizadosVenta guardar(TrabajosTercerizadosVenta trabajosTercerizadosVenta) {}
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularCostoTotal(){}
    private BigDecimal calcularCostoUnitario(){}

}
