package com.drover.demo.backend.service;

import java.time.LocalDate;
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
    public List<Revendedor> listarPorFecha(LocalDate alta){
        if (alta == null) {
           throw new IllegalArgumentException("La fecha de alta es obligatoria para realizar la consulta.");
        }
        return revendedorRepository.findByFechaAlta(alta);
    }    
}
