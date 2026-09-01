package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.CostosEscalaTercerizado;
import com.drover.demo.backend.repository.CostosEscalaTercerizadoRepository;

@Service
public class Costos_escala_tercerizadoService {

    private final CostosEscalaTercerizadoRepository costosEscalaTercerizadoRepository;

    public Costos_escala_tercerizadoService(CostosEscalaTercerizadoRepository costosEscalaTercerizadoRepository) {
        this.costosEscalaTercerizadoRepository = costosEscalaTercerizadoRepository;
    }

    public List<CostosEscalaTercerizado> listar() {}
    public Optional<CostosEscalaTercerizado> buscarPorId(Long id) { }
    public CostosEscalaTercerizado guardar(CostosEscalaTercerizado costosEscalaTercerizado) {}
    public void eliminarPorId(Long id) {}
     
    private BigDecimal calcularPrecioUnitario(){}
}
