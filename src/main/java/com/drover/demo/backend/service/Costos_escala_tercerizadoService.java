package com.drover.demo.backend.service;

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

    public List<Costos_escala_tercerizado> listar() {
        return costosEscalaTercerizadoRepository.findAll();
    }

    public Optional<Costos_escala_tercerizado> buscarPorId(Long id) {
        return costosEscalaTercerizadoRepository.findById(id);
    }

    public Costos_escala_tercerizado guardar(Costos_escala_tercerizado costosEscalaTercerizado) {
        return costosEscalaTercerizadoRepository.save(costosEscalaTercerizado);
    }

    public void eliminarPorId(Long id) {
        costosEscalaTercerizadoRepository.deleteById(id);
    }
}
