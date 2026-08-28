package com.drover.demo.backend.service;

import java.time.LocalDate;
import java.util.List;


import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Vendedor;
import com.drover.demo.backend.repository.PersonaRepository;
import com.drover.demo.backend.repository.VendedorRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    public VendedorService(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    public List<Vendedor> listar() {
        return vendedorRepository.findAll();    
    }

    public List<Vendedor> listarFechaAlta(LocalDate alta) {
        if (alta == null) {
            throw new IllegalArgumentException("La fecha de alta es obligatoria para realizar la consulta.");
        }
        return vendedorRepository.findByFechaAlta(alta);
    }
   
}

