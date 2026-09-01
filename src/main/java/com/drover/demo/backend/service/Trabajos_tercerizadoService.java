package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.TrabajosTercerizado;
import com.drover.demo.backend.repository.TrabajosTercerizadoRepository;

@Service
public class Trabajos_tercerizadoService {

    private final TrabajosTercerizadoRepository trabajosTercerizadoRepository;

    public Trabajos_tercerizadoService(TrabajosTercerizadoRepository trabajosTercerizadoRepository) {
        this.trabajosTercerizadoRepository = trabajosTercerizadoRepository;
    }

    public List<TrabajosTercerizado> listar() {}
    public Optional<TrabajosTercerizado> buscarPorId(Long id) {}
    public TrabajosTercerizado guardar(TrabajosTercerizado trabajosTercerizado) {}
    public void eliminarPorId(Long id) {}

    
}
