package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Ventas;
import com.drover.demo.backend.repository.VentasRepository;

@Service
public class VentasService {

    private final VentasRepository ventasRepository;

    public VentasService(VentasRepository ventasRepository) {
        this.ventasRepository = ventasRepository;
    }

    public List<Ventas> listar() {
        return ventasRepository.findAll();
    }

    public Optional<Ventas> buscarPorId(Long id) {
        return ventasRepository.findById(id);
    }

    public Ventas guardar(Ventas ventas) {
        return ventasRepository.save(ventas);
    }

    public void eliminarPorId(Long id) {
        ventasRepository.deleteById(id);
    }
}
