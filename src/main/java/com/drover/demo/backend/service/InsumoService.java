package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.InsumoRepository;

@Service
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public Optional<Insumo> buscarPorId(Long id) {
        return insumoRepository.findById(id);
    }

    public Insumo guardar(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    public void eliminarPorId(Long id) {
        insumoRepository.deleteById(id);
    }
}
