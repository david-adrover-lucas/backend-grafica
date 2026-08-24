package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Trabajos_tercerizado;
import com.drover.demo.backend.repository.Trabajos_tercerizadoRepository;

@Service
public class Trabajos_tercerizadoService {

    private final Trabajos_tercerizadoRepository trabajosTercerizadoRepository;

    public Trabajos_tercerizadoService(Trabajos_tercerizadoRepository trabajosTercerizadoRepository) {
        this.trabajosTercerizadoRepository = trabajosTercerizadoRepository;
    }

    public List<Trabajos_tercerizado> listar() {
        return trabajosTercerizadoRepository.findAll();
    }

    public Optional<Trabajos_tercerizado> buscarPorId(Long id) {
        return trabajosTercerizadoRepository.findById(id);
    }

    public Trabajos_tercerizado guardar(Trabajos_tercerizado trabajosTercerizado) {
        return trabajosTercerizadoRepository.save(trabajosTercerizado);
    }

    public void eliminarPorId(Long id) {
        trabajosTercerizadoRepository.deleteById(id);
    }
}
