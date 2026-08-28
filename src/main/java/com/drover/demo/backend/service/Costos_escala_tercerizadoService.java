package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Costos_escala_tercerizado;
import com.drover.demo.backend.repository.Costos_escala_tercerizadoRepository;

@Service
public class Costos_escala_tercerizadoService {

    private final Costos_escala_tercerizadoRepository costosEscalaTercerizadoRepository;

    public Costos_escala_tercerizadoService(Costos_escala_tercerizadoRepository costosEscalaTercerizadoRepository) {
        this.costosEscalaTercerizadoRepository = costosEscalaTercerizadoRepository;
    }

    public List<Costos_escala_tercerizado> listar() {}
    public Optional<Costos_escala_tercerizado> buscarPorId(Long id) { }
    public Costos_escala_tercerizado guardar(Costos_escala_tercerizado costosEscalaTercerizado) {}
    public void eliminarPorId(Long id) {}
     
    private BigDecimal calcularPrecioUnitario(){}
}
