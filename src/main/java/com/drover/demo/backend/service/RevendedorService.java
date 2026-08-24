package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Revendedor;
import com.drover.demo.backend.repository.RevendedorRepository;

@Service
public class RevendedorService {

    private final RevendedorRepository revendedorRepository;

    public RevendedorService(RevendedorRepository revendedorRepository) {
        this.revendedorRepository = revendedorRepository;
    }

    public List<Revendedor> listar() {
        return revendedorRepository.findAll();
    }

    public Optional<Revendedor> buscarPorId(Long id) {
        return revendedorRepository.findById(id);
    }

    public Revendedor guardar(Revendedor revendedor) {
        return revendedorRepository.save(revendedor);
    }

    public void eliminarPorId(Long id) {
        revendedorRepository.deleteById(id);
    }
}
