package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Vendedor;
import com.drover.demo.backend.repository.VendedorRepository;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public List<Vendedor> listar() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> buscarPorId(Long id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor guardar(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public void eliminarPorId(Long id) {
        vendedorRepository.deleteById(id);
    }
}
