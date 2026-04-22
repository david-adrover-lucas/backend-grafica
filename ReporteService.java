package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Reporte;
import com.drover.demo.backend.repository.ReporteRepository;


@Service
public class ReporteService {

    private final ReporteRepository repository;

    public ReporteService(ReporteRepository repository) {
        this.repository = repository;
    }

    public Reporte guardar(Reporte reporte) {
        return repository.save(reporte);
    }


    public List<Reporte> listarPorTipo(String tipo) {
        return repository.findByTipo(tipo);
    }


}
    

