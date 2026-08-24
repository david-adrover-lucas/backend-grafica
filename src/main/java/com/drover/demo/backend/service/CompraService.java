package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Compra;
import com.drover.demo.backend.repository.CompraRepository;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }

    public Compra guardar(Compra compra) {
        return compraRepository.save(compra);
    }

    public void eliminarPorId(Long id) {
        compraRepository.deleteById(id);
    }
}
